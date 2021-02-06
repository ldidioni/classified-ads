package com.ldidioni.classifiedads.controllers;

import java.util.Map;
import java.util.Optional;

import com.ldidioni.classifiedads.models.Photo;
import com.ldidioni.classifiedads.models.Tag;
import com.ldidioni.classifiedads.models.User;
import com.ldidioni.classifiedads.repositories.CategoryRepository;
import com.ldidioni.classifiedads.repositories.PhotoRepository;
import com.ldidioni.classifiedads.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ldidioni.classifiedads.models.Ad;
import com.ldidioni.classifiedads.repositories.AdRepository;
import com.ldidioni.classifiedads.services.UserService;

import static java.lang.Double.parseDouble;


@Controller
public class AdController
{
    @Autowired
    AdRepository adRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    CategoryRepository categoryRepository;

    //User management via UserService class
    @Autowired
    private UserService userService;

    @GetMapping("/ads")
    public String getAllAds(Map<String, Object> model) {

        model.put("ads", adRepository.findAll());

        model.put("searchedAd", new Ad());
        model.put("categories", categoryRepository.findAll());
        model.put("tags", tagRepository.findAll());

        return "ads/index";
    }

    @GetMapping("/")
    public String home() {

        return "redirect:/ads";
    }

    @GetMapping("/ads/new")
    public String createNewAd(Model model) {

        model.addAttribute("ad", new Ad()); // NOT NEEDED???
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());

        return "ads/form";
    }

    @PostMapping("/ads/new")
    public String processNewAd(@RequestParam("title")String title,
                              @RequestParam("description")String description,
                              @RequestParam("priceAsString")String priceAsString,
                              @RequestParam("category")String category,
                              @RequestParam("photos")String[] photos,
                              @RequestParam("tags")String[] tags)
    {
        Double price = parseDouble(priceAsString);

        inputValidation(title, description, price, category);

        Ad ad = new Ad(title, description, price);

        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User seller = userService.findUserByEmail(auth.getName());
        User seller = userService.currentUser();
        ad.setSeller(seller);
        adRepository.save(ad);

        for (String photo_url : photos)
        {
            Photo photo = new Photo(photo_url);
            photo.setAd(ad);
            photoRepository.save(photo);
        }

        // tags are managed by admins, users pick from predefined list of tags
        for (String tagName : tags)
        {
            Tag tag = tagRepository.findByName(tagName);
            tag.linkAd(ad);
        }

        return "redirect:/ads/${ad.getId()}";
    }

    @GetMapping("/ads/{adId}")
    public String getAd(@PathVariable("adId")int adId, Map<String, Object> model) {
        Optional ad = adRepository.findById(adId);
        model.put("ad", ad);

        return "ads/show";
    }

    @DeleteMapping("/ads/{adId}")
    public String deleteAd(@PathVariable("adId")int adId) {
        adRepository.findById(adId).ifPresent(ad -> adRepository.delete(ad));

        return "redirect:/ads";
    }

    private void inputValidation(String title, String description, double asked_price, String category)
    {
    }
}
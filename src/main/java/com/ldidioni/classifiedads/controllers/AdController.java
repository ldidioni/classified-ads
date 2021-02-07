package com.ldidioni.classifiedads.controllers;

import java.util.*;

import com.ldidioni.classifiedads.models.*;
import com.ldidioni.classifiedads.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ldidioni.classifiedads.services.UserService;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;


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

    @Autowired
    private UserRepository userRepository;

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
    public String createNewTag(Model model)
    {

        model.addAttribute("adForm", new AdForm());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());

        return "ads/form";
    }

    @PostMapping("/ads/new")
    public String processNewTag(@ModelAttribute AdForm adForm)
    {
        User seller = userService.currentUser();
        //User seller;
        //userRepository.findById(1).ifPresent(user -> seller = user);

        Ad ad = new Ad( adForm.getAd().getTitle(),
                        adForm.getAd().getDescription(),
                        adForm.getAd().getPrice(),
                        userRepository.getOne(1),   // HACK: should be: seller
                        adForm.getAd().getCategory());

        adRepository.save(ad);

        for (String photoUrl : adForm.getPhotoUrls())
        {
            if(photoUrl != "")
            {
                Photo photo = new Photo(photoUrl);
                photo.setAd(ad);
                photoRepository.save(photo);
            }
        }

        // tags are managed by admins, users pick from predefined list of tags
        for (Tag tag : adForm.getAd().getTags())
        {
            tag.linkAd(ad);
            tagRepository.save(tag);
        }

        return "redirect:/ads";
    }
/*
    @PostMapping("/ads/new")
    public String processNewAd(@RequestParam String title, @RequestParam String description, @RequestParam double price,
                               @RequestParam int category, @RequestParam(required = false) int[] tags,
                               @RequestParam(required = false) String[] photos)
    {
        //inputValidation(title, description, priceAsDouble, category);

        Ad ad = new Ad(title, description, price);

        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //User seller = userService.findUserByEmail(auth.getName());
        User seller = userService.currentUser();
        ad.setSeller(seller);

        Category existingCategory = categoryRepository.getOne(category);
        ad.setCategory(existingCategory);
        adRepository.save(ad);

        for (String photo_url : photos)
        {
            Photo photo = new Photo(photo_url);
            photo.setAd(ad);
            photoRepository.save(photo);
        }

        // tags are managed by admins, users pick from predefined list of tags
        for (int tag : tags)
        {
            Tag existingTag = tagRepository.getOne(tag);
            existingTag.linkAd(ad);
        }

        return "redirect:/ads/${ad.getId()}";
    }
*/
    @GetMapping("/ads/{id}")
    public String getAd(@PathVariable("id")int id, Map<String, Object> model) {
        Optional ad = adRepository.findById(id);
        model.put("ad", ad);

        return "ads/show";
    }

    @DeleteMapping("/ads/{id}")
    public String deleteAd(@PathVariable("id")int id) {
        adRepository.findById(id).ifPresent(ad -> adRepository.delete(ad));

        return "redirect:/ads";
    }

    private void inputValidation(String title, String description, double asked_price, String category)
    {
    }
}
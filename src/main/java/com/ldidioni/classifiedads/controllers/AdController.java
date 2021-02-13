package com.ldidioni.classifiedads.controllers;

import java.util.*;

import com.ldidioni.classifiedads.models.*;
import com.ldidioni.classifiedads.repositories.*;
import com.ldidioni.classifiedads.services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ldidioni.classifiedads.services.UserService;


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

    @Autowired
    AdService adService;

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
    public String createNewAd(Model model)
    {

        model.addAttribute("adForm", new AdForm());
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());

        return "ads/form";
    }

    @PostMapping("/ads/new")
    public String processNewAd(@ModelAttribute AdForm adForm)
    {
        User seller = userService.currentUser();
        //User seller;
        //userRepository.findById(1).ifPresent(user -> seller = user);

        Ad ad = new Ad( adForm.getAd().getTitle(),
                        adForm.getAd().getDescription(),
                        adForm.getAd().getPrice(),
                        userRepository.getOne(1),   // HACK: should be: seller
                        adForm.getAd().getCategory());

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
            //tagRepository.save(tag);
        }

        adRepository.save(ad);

        return "redirect:/ads";
    }

    @GetMapping("/ads/{id}/edit")
    public String editAd(@PathVariable("id")int id, Model model)
    {
        Ad ad = adRepository.getOne(id);
        List<Photo> photos = ad.getPhotos();
        String[] photoUrls = new String[3];

        for(int i = 0 ; i < photos.size() ; i++) {
            photoUrls[i] = photos.get(i).getImageUrl();
        }

        model.addAttribute("adForm", new AdForm(ad, photoUrls));
        model.addAttribute("categories", categoryRepository.findAll());
        model.addAttribute("tags", tagRepository.findAll());

        return "ads/form";
    }

    @PostMapping("/ads/{id}/edit")
    public String processEditAd(@PathVariable("id")int id, @ModelAttribute AdForm adForm)
    {
        //User seller = userService.currentUser();
        //User seller;
        userRepository.findById(1).ifPresent(user -> adForm.getAd().setSeller(user));

        Ad originalAd = adRepository.getOne(id);

        // remove all photos linked to original ad
        originalAd.getPhotos().clear();
        photoRepository.deleteByAd(originalAd);

        for (String photoUrl : adForm.getPhotoUrls())
        {
            if(photoUrl != "")
            {
                Photo photo = new Photo(photoUrl);
                photo.setAd(adForm.getAd());
                photoRepository.save(photo);
            }
        }

        originalAd.getTags().clear();

        // remove all tags linked to original ad and link
        for (Tag tag : originalAd.getTags())
        {
            tag.removeAd(originalAd);
        }

        // add new tags
        for (Tag tag : adForm.getAd().getTags())
        {
            tag.linkAd(adForm.getAd());
        }

        adRepository.findById(id).ifPresent(existingAd -> adService.update(id, adForm.getAd()));

        return "redirect:/ads";
    }

    @GetMapping("/ads/{id}")
    public String getAd(@PathVariable("id")int id, Map<String, Object> model) {
        Ad ad = adRepository.getOne(id);
        model.put("ad", ad);
        model.put("seller", ad.getSeller());
        model.put("tags", ad.getTags());
        model.put("photos", photoRepository.findByAd(ad));

        return "ads/show";
    }

    @DeleteMapping("/ads/{id}")
    public String deleteAd(@PathVariable("id")int id) {
        adRepository.findById(id).ifPresent(ad -> {

            for (Tag tag : ad.getTags())
            {
                tag.removeAd(ad);
            }

            photoRepository.deleteByAd(ad);
            adRepository.delete(ad);
        });

        return "redirect:/ads";
    }

    @PostMapping("/ads/search")
    public String processSearchAd(@ModelAttribute Ad searchedAd, Map<String, Object> model)
    {
        List<Ad> foundAds = new ArrayList<>();

        if(searchedAd.getCategory() == null && searchedAd.getTags().isEmpty()) {

            foundAds = adRepository.findDistinctByTitleIgnoreCaseContainingOrDescriptionIgnoreCaseContaining(searchedAd.getTitle(), searchedAd.getTitle());
        }
        else {

            String[] tagNames = new String[0];
            int index = 0;

            for (Tag tag : searchedAd.getTags())
            {
                tagNames[index++] = tag.getName();
            }

            foundAds = adRepository.findCustom( searchedAd.getTitle(), searchedAd.getCategory().getName()); //, tagNames
        }

        model.put("ads", foundAds);

        model.put("searchedAd", new Ad());
        model.put("categories", categoryRepository.findAll());
        model.put("tags", tagRepository.findAll());

        return "ads/index";
    }

    private void inputValidation(String title, String description, double asked_price, String category)
    {
    }
}
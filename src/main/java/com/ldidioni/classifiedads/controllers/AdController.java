package com.ldidioni.classifiedads.controllers;

import java.util.*;

import com.ldidioni.classifiedads.models.*;
import com.ldidioni.classifiedads.repositories.*;
import com.ldidioni.classifiedads.services.AdService;
import org.springframework.beans.factory.annotation.Autowired;
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
        model.put("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.put("isAdmin", userService.isAdmin());

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
        model.addAttribute("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.addAttribute("isAdmin", userService.isAdmin());

        return "ads/form";
    }

    @PostMapping("/ads/new")
    public String processNewAd(@ModelAttribute AdForm adForm)
    {
        User seller = userService.findUserByUsername(userService.getCurrentUsername());

        Ad ad = new Ad( adForm.getAd().getTitle(),
                        adForm.getAd().getDescription(),
                        adForm.getAd().getPrice(),
                        seller,
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
        model.addAttribute("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.addAttribute("isAdmin", userService.isAdmin());

        return "ads/form";
    }

    @PostMapping("/ads/{id}/edit")
    public String processEditAd(@PathVariable("id")int id, @ModelAttribute AdForm adForm)
    {
        User seller = userService.findUserByUsername(userService.getCurrentUsername());
        adForm.getAd().setSeller(seller);

        adService.update(id, adForm.getAd());
        Ad updatedAd = adRepository.getOne(id);
        // adForm.getAd() does not contain any photo, thus at this point, updatedAd.getPhotos() returns null

        // remove all dangling photos
        photoRepository.deleteByAd(updatedAd);

        // add new photos as found in adform object to the updated ad
        for (String photoUrl : adForm.getPhotoUrls())
        {
            if(photoUrl != "")
            {
                Photo photo = new Photo(photoUrl);
                photo.setAd(updatedAd);
                photoRepository.save(photo);
            }
        }

        // remove all tags which are linked to the ad
        Set<Tag> tags = new HashSet<>(updatedAd.getTags());

        for (Tag tag : tags)
        {
            tag.removeAd(updatedAd);
        }

        // only link the tags as found in adform object to the updated ad
        for (Tag tag : adForm.getAd().getTags())
        {
            tag.linkAd(updatedAd);
        }

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

            Set<Tag> tags = new HashSet<>(ad.getTags());

            for (Tag tag : tags)
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

        if(searchedAd.getTags().isEmpty()) {

            foundAds = adRepository.findCustom(searchedAd.getTitle(), searchedAd.getCategory().getName());
        }
        else {

            List<String> tagNames = new ArrayList<>();

            for (Tag tag : searchedAd.getTags())
            {
                tagNames.add(tag.getName());
            }

            foundAds = adRepository.findCustomWithTags(searchedAd.getTitle(), searchedAd.getCategory().getName(), tagNames);
        }

        model.put("ads", foundAds.isEmpty() ? adRepository.findAll() : foundAds);

        model.put("searchedAd", new Ad());
        model.put("categories", categoryRepository.findAll());
        model.put("tags", tagRepository.findAll());
        model.put("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.put("isAdmin", userService.isAdmin());

        return "ads/index";
    }

    private void inputValidation(String title, String description, double asked_price, String category)
    {
    }
}
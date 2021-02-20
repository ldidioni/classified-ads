package com.ldidioni.classifiedads.services;

import com.ldidioni.classifiedads.models.Ad;
import com.ldidioni.classifiedads.repositories.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service("adService")
public class AdService
{
    @Autowired
    AdRepository adRepository;

    public void update(int id, Ad ad) {
        adRepository.findById(id).ifPresent(existingAd -> {
            existingAd.setTitle(ad.getTitle());
            existingAd.setDescription(ad.getDescription());
            existingAd.setPrice(ad.getPrice());
            existingAd.setCategory(ad.getCategory());
            existingAd.setTags(new HashSet(ad.getTags()));
            existingAd.setPhotos(ad.getPhotos());
            adRepository.save(existingAd);
        });
    };
}

package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Ad;
import com.ldidioni.classifiedads.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhotoRepository extends JpaRepository<Photo, Integer>
{
    List<Photo> findByAd(Ad ad);
}

package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer>
{

}

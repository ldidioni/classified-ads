package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Ad;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdRepository extends JpaRepository<Ad, Integer>
{

}

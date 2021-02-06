package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer>
{
    Category findByName(String name);
}

package com.ldidioni.classifiedads.repositories;

import com.ldidioni.classifiedads.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer>
{
    Tag findByName(String name);

    //void update(int id, Tag tag);
}

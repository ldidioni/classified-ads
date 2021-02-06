package com.ldidioni.classifiedads.services;

import com.ldidioni.classifiedads.models.Tag;
import com.ldidioni.classifiedads.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tagService")
public class TagService
{
    @Autowired
    TagRepository tagRepository;

    public void update(int id, Tag tag) {
        tagRepository.findById(id).ifPresent(existingTag -> {
            existingTag.setName(tag.getName());
            tagRepository.save(existingTag);
        });
    };
}

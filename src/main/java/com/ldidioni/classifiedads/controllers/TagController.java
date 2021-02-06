package com.ldidioni.classifiedads.controllers;

import com.ldidioni.classifiedads.models.Tag;
import com.ldidioni.classifiedads.repositories.TagRepository;
import com.ldidioni.classifiedads.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@Controller
public class TagController
{
    @Autowired
    TagRepository tagRepository;

    @Autowired
    TagService tagService;

    @GetMapping("/tags")
    public String getAllTags(Map<String, Object> model)
    {
        model.put("tags", tagRepository.findAll());

        return "tags/index";
    }

    @GetMapping("/tags/new")
    public String createNewTag(Model model)
    {
        model.addAttribute("tag", new Tag());

        return "tags/form";
    }

    @PostMapping("/tags/new")
    public String processNewTag(@ModelAttribute Tag tag)
    {
        tagRepository.save(tag);

        return "redirect:/tags";
    }

    @GetMapping("/tags/{id}")
    public String seeTag(@PathVariable("id")int id, Model model)
    {
        Tag tag = tagRepository.getOne(id);
        model.addAttribute("tag", tag);

        return "tags/form";
    }

    @PostMapping("/tags/{id}")
    public String updateTag(@PathVariable("id")int id, @ModelAttribute Tag tag)
    {
        tagRepository.findById(id).ifPresent(existingTag -> tagService.update(existingTag.getId(), tag));

        return "redirect:/tags";
    }

    @DeleteMapping("/tags/{id}")
    public String deleteTag(@PathVariable("id")int id)
    {
        tagRepository.findById(id).ifPresent(tag -> tagRepository.delete(tag));

        return "redirect:/tags";
    }
}

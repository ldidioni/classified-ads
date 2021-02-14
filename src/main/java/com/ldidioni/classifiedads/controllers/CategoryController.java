package com.ldidioni.classifiedads.controllers;

import com.ldidioni.classifiedads.models.Category;
import com.ldidioni.classifiedads.repositories.CategoryRepository;
import com.ldidioni.classifiedads.services.CategoryService;
import com.ldidioni.classifiedads.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class CategoryController
{
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryService categoryService;

    @Autowired
    UserService userService;

    @GetMapping("/categories")
    public String getAllCategories(Map<String, Object> model)
    {
        model.put("categories", categoryRepository.findAll());
        model.put("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.put("isAdmin", userService.isAdmin());

        return "categories/index";
    }

    @GetMapping("/categories/new")
    public String createNewCategory(Model model)
    {
        model.addAttribute("category", new Category());
        model.addAttribute("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.addAttribute("isAdmin", userService.isAdmin());

        return "categories/form";
    }

    @PostMapping("/categories/new")
    public String processNewCategory(@ModelAttribute Category category)
    {
        categoryRepository.save(category);

        return "redirect:/categories";
    }

    @GetMapping("/categories/{id}")
    public String seeCategory(@PathVariable("id")int id, Model model)
    {
        Category category = categoryRepository.getOne(id);
        model.addAttribute("category", category);
        model.addAttribute("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.addAttribute("isAdmin", userService.isAdmin());

        return "categories/form";
    }

    @PostMapping("/categories/{id}")
    public String updateCategory(@PathVariable("id")int id, @ModelAttribute Category category)
    {
        categoryRepository.findById(id).ifPresent(existingCategory -> categoryService.update(existingCategory.getId(), category));

        return "redirect:/categories";
    }

    @DeleteMapping("/categories/{id}")
    public String deleteCategory(@PathVariable("id")int id)
    {
        categoryRepository.findById(id).ifPresent(category -> categoryRepository.delete(category));

        return "redirect:/categories";
    }
}

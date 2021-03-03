package com.ldidioni.classifiedads.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ldidioni.classifiedads.models.Ad;
import com.ldidioni.classifiedads.models.Role;
import com.ldidioni.classifiedads.models.Tag;
import com.ldidioni.classifiedads.repositories.AdRepository;
import com.ldidioni.classifiedads.repositories.PhotoRepository;
import com.ldidioni.classifiedads.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import com.ldidioni.classifiedads.models.User;
import com.ldidioni.classifiedads.services.UserService;


@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdRepository adRepository;

    @GetMapping("/login")
    public String login(Map<String, Object> model)
    {
        return "users/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid User user, BindingResult bindingResult, Map<String, Object> model) {

        User userExists = userService.findUserByUsername(user.getUsername());

        if (userExists == null) {
            return ("redirect:/users/login?error=true");
        }
        else
        {
            model.put("msg", "You have successfully logged in!");
            model.put("user", userExists);

            return "ads/index";
        }
    }

    @GetMapping("/signup")
    public String signup(Map<String, Object> model) {
        User user = new User();
        model.put("user", user);

        return "users/signup";
    }

    @PostMapping("/signup")
    public String createUser(@Valid User user, BindingResult bindingResult, Map<String, Object> model) {

        User userExists = userService.findUserByUsername(user.getUsername());

        if (userExists != null) {
            return ("redirect:/signup?error=true");
        }

        if (bindingResult.hasErrors())
        {
            return ("redirect:/signup?error=true");
        }
        else
        {
            userService.saveUser(user);
            model.put("msg", "You has successfully registered !");
            model.put("user", new User());

            return "redirect:/login";
        }
    }

    @GetMapping("/users")
    public String getAllUsers(Map<String, Object> model)
    {
        model.put("users", userRepository.findAll());
        model.put("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.put("isAdmin", userService.isAdmin());

        return "users/index";
    }

    @GetMapping("/users/{id}")
    public String seeUser(@PathVariable("id")int id, Model model)
    {
        User user = userRepository.getOne(id);
        model.addAttribute("user", user);
        model.addAttribute("currentUser", userService.findUserByUsername(userService.getCurrentUsername()));
        model.addAttribute("isAdmin", userService.isAdmin());

        return "users/form";
    }

    @PostMapping("/users/{id}")
    public String updateUser(@PathVariable("id")int id, @ModelAttribute User user)
    {
        userRepository.findById(id).ifPresent(existingUser -> userService.update(existingUser.getId(), user));

        return "redirect:/users";
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable("id")int id)
    {
        userRepository.findById(id).ifPresent(user -> {

            List<Ad> ads = adRepository.findBySeller(user);

            for (Ad ad : ads) {

                Set<Tag> tags = new HashSet<>(ad.getTags());

                for (Tag tag : tags)
                {
                    tag.removeAd(ad);
                }
                photoRepository.deleteByAd(ad);
            }

            adRepository.deleteBySeller(user);

            Set<Role> roles = new HashSet<>(user.getRoles());

            for (Role role : roles)
            {
                role.removeUser(user);
            }

            userRepository.delete(user);
        });

        return "redirect:/users";
    }
}
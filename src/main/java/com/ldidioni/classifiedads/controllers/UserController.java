package com.ldidioni.classifiedads.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.validation.Valid;

import com.ldidioni.classifiedads.models.User;
import com.ldidioni.classifiedads.services.UserService;


@Controller
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Map<String, Object> model)
    {
        return "users/login";
    }

    @PostMapping("/login")
    public String processLogin(@Valid User user, BindingResult bindingResult, Map<String, Object> model) {

        User userExists = userService.findUserByEmail(user.getEmail());

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

        User userExists = userService.findUserByEmail(user.getEmail());

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
}
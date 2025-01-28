package com.huan1645.TWDevJob.controller;


import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.entity.UserType;
import com.huan1645.TWDevJob.service.UserService;
import com.huan1645.TWDevJob.service.UserTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserTypeService userTypeService;
    private final UserService userService;

    @Autowired
    public UserController(UserTypeService userTypeService, UserService userService) {
        this.userTypeService = userTypeService;
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegistrationForm(Model model){
        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register/new")
    public String userRegistration(@Valid User user){
        userService.registerUser(user);
        return "dashboard";
    }
}

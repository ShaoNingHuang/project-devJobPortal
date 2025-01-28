package com.huan1645.TWDevJob.controller;


import com.huan1645.TWDevJob.entity.User;
import com.huan1645.TWDevJob.entity.UserType;
import com.huan1645.TWDevJob.service.UserTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UserController {
    private final UserTypeService userTypeService;

    @Autowired
    public UserController(UserTypeService userTypeService) {
        this.userTypeService = userTypeService;
    }

    @GetMapping("/register")
    public String getRegistrationForm(Model model){
        List<UserType> userTypes = userTypeService.getAll();
        model.addAttribute("userTypes", userTypes);
        model.addAttribute("user", new User());
        return "register";
    }
}

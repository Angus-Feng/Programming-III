package com.simpleform.simpleform.controller;

import com.simpleform.simpleform.entity.RegisteredUser;
import com.simpleform.simpleform.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public ModelAndView homepage() {
        ModelAndView mav = new ModelAndView("register");
        RegisteredUser newRegisteredUser = new RegisteredUser();
        mav.addObject("registeredUser", newRegisteredUser);
        return mav;
    }

    @PostMapping("/register")
    public String register(@Valid RegisteredUser registeredUser, BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }
        userRepository.save(registeredUser);
        return "success";
    }
}

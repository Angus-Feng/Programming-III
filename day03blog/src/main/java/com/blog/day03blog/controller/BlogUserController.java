package com.blog.day03blog.controller;

import com.blog.day03blog.entity.BlogUser;
import com.blog.day03blog.service.BlogUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@AllArgsConstructor
public class BlogUserController {

    @Autowired
    private final BlogUserService blogUserService;


    @GetMapping("/register")
    public ModelAndView registerPage() {
        ModelAndView mav = new ModelAndView("register");
        BlogUser newUser = new BlogUser();
        mav.addObject("blogUser", newUser);
        return mav;
    }

    @PostMapping("/registration")
    public String register(@Valid BlogUser blogUser, BindingResult result, Principal principal) {
        return blogUserService.register(blogUser, result);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login_err")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        return "login";
    }

//    @PostMapping("/logout")
//    public String logout() {
//        return "redirect:/";
//    }
}

package com.blog.day03blog.controller;

import com.blog.day03blog.entity.Article;
import com.blog.day03blog.service.BlogService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;


@Controller
@AllArgsConstructor
public class BlogController {

    @Autowired
    private final BlogService blogService;

    @GetMapping("/")
    public ModelAndView getIndex() {
        return blogService.getAllArticles();
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }

    @GetMapping("/article")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ModelAndView showArticle(@RequestParam Long id) {
        return blogService.getArticleById(id);
    }

    @GetMapping("/add_article")
    public ModelAndView showArticleForm() {
        return blogService.showArticleForm();
    }

    @PostMapping("/add_article")
    @PreAuthorize(value = "hasRole('READER')")
    public String addArticle(@Valid Article article, BindingResult result, Principal principal) {
        return blogService.addArticle(article, result, principal);
    }

}

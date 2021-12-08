package com.midtermsecrets.midtermsecrets.controller;

import com.midtermsecrets.midtermsecrets.entity.Secret;
import com.midtermsecrets.midtermsecrets.service.SecretService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class SecretController {

    private final SecretService secretService;

    @Autowired
    public SecretController(SecretService secretService) {
        this.secretService = secretService;
    }

    @GetMapping("/")
    public ModelAndView index() {

        return secretService.getIndex();
    }

    @GetMapping("/create")
    public ModelAndView pageCreate() {
        ModelAndView mav = new ModelAndView("create");
        Secret secret = new Secret();
        mav.addObject("secret", secret);
        return mav;
    }

    @PostMapping("/create")
    public String createSecret(@Valid Secret secret, BindingResult result, RedirectAttributes redirectAttributes) {

        return secretService.addSecret(secret, result, redirectAttributes);
    }

    @GetMapping("/create_success")
    public String createSuccess() {
        return "create_success";
    }

    @GetMapping("/read")
    public String readSecret() {
        return "read";
    }

    @PostMapping("/read")
    public String getSecret(String readToken, RedirectAttributes redirectAttributes) {
        return secretService.getSecret(readToken, redirectAttributes);
    }

    @GetMapping("/update")
    public ModelAndView pageUpdate() {
        ModelAndView mav = new ModelAndView("update");
        Secret secret = new Secret();
        mav.addObject("secret", secret);
        return mav;
    }

    @PostMapping("/update")
    public String updateSecret(@Valid Secret request, BindingResult result, RedirectAttributes redirectAttributes) {

        return secretService.updateSecret(request, result, redirectAttributes);
    }

    @GetMapping("/cleanexpired")
    public ModelAndView cleanExpired() {
        ModelAndView mav = new ModelAndView("clean_expired");
        mav.addObject("cleaned", "Expired secrets have been cleaned");
        secretService.deleteExpiredSecrets();
        return mav;
    }
}

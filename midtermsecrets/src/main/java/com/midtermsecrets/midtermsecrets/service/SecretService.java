package com.midtermsecrets.midtermsecrets.service;

import com.midtermsecrets.midtermsecrets.entity.Secret;
import com.midtermsecrets.midtermsecrets.repository.SecretRepository;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class SecretService {

    private final SecretRepository secretRepository;

    @Autowired
    public SecretService(SecretRepository secretRepository) {
        this.secretRepository = secretRepository;
    }

    public ModelAndView getIndex() {

        ModelAndView mav = new ModelAndView("index");
        List<Secret> unreadList = secretRepository.findAllByExpiryDTIsAfterAndHasBeenRead(LocalDateTime.now(), false).get();
        List<Secret> unExpiredList = secretRepository.findAllByExpiryDTIsAfter(LocalDateTime.now()).get();
        mav.addObject("unread", unreadList.size());
        mav.addObject("unExpired", unExpiredList.size());
        return mav;
    }


    public String addSecret(@Valid Secret secret, BindingResult result, RedirectAttributes redirectAttributes) {

        if (!secret.getStringExpiryDT().isEmpty()) {
            secret.setExpiryDT(LocalDateTime.parse(secret.getStringExpiryDT()));
        }
        if (secret.getExpiryDT() == null || secret.getExpiryDT().isBefore(LocalDateTime.now())) {
            FieldError error = new FieldError("secret", "expiryDT", "The expiry time can not be earlier than current time");
            result.addError(error);
        }

        if (result.hasErrors()) {
            return "create";
        }

        secret.setCreatedDT(LocalDateTime.now());

        // make sure the tokens are unique
        String readToken = randomString();
        while(secretRepository.findBySecretToRead(readToken).isPresent()) {
            readToken = randomString();
        }
        String updateToken = randomString();
        while (secretRepository.findBySecretToUpdate(updateToken).isPresent()) {
            updateToken = randomString();
        }
        // Strip tags
        secret.setBody(Jsoup.clean(secret.getBody(), Safelist.simpleText()));
        secret.setSecretToRead(readToken);
        secret.setSecretToUpdate(updateToken);
        redirectAttributes.addFlashAttribute("readToken", readToken);
        redirectAttributes.addFlashAttribute("updateToken", updateToken);
        secretRepository.save(secret);
        return "redirect:/create_success";
    }

    public String getSecret(String readToken, RedirectAttributes redirectAttributes) {
        if (!secretRepository.findBySecretToRead(readToken).isPresent()) {
            redirectAttributes.addFlashAttribute("notFound", "Invalid code, please try again.");
            return "redirect:/read";
        }

        Secret secret = secretRepository.findBySecretToRead(readToken).get();
        if (secret.getExpiryDT().isBefore(LocalDateTime.now())) {
            redirectAttributes.addFlashAttribute("notFound", "This secret is expired.");
            return "redirect:/read";
        }

        secretRepository.hasBeenRead(readToken);

        redirectAttributes.addFlashAttribute("secret", secret);
        return "redirect:/read";
    }


    public String updateSecret(@Valid Secret request, BindingResult secret, RedirectAttributes redirectAttributes) {

        if (!secretRepository.findBySecretToUpdate(request.getSecretToUpdate()).isPresent()) {
            FieldError error = new FieldError("request", "secretToUpdate", "Invalid secret code");
            secret.addError(error);
            return "update";
        }
        Secret origSecret = secretRepository.findBySecretToUpdate(request.getSecretToUpdate()).get();

        if (!request.getStringExpiryDT().isEmpty()) {
            LocalDateTime dateTime = LocalDateTime.parse(request.getStringExpiryDT());
            if (dateTime.isBefore(LocalDateTime.now())) {
                FieldError error = new FieldError("request", "stringExpiryDT", "The expiry time can not be earlier than current time");
                secret.addError(error);
            } else {
                origSecret.setExpiryDT(dateTime);
            }
        }

        if (secret.hasErrors()) {
            return "update";
        }

        origSecret.setBody(Jsoup.clean(request.getBody(), Safelist.simpleText()));
        secretRepository.save(origSecret);
        redirectAttributes.addFlashAttribute("success", "Update succeeded");
        return "redirect:/update";
    }

    @Transactional
    public void deleteExpiredSecrets() {
        secretRepository.deleteByExpiryDTBefore(LocalDateTime.now());
    }

    private String randomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}

package com.blog.day03blog.service;

import com.blog.day03blog.entity.BlogUser;
import com.blog.day03blog.repo.BlogUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;


import javax.validation.Valid;

@Service
@AllArgsConstructor
public class BlogUserService implements UserDetailsService {

    @Autowired
    private final BlogUserRepository blogUserRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final static String PW_ERROR = "Password must be at least 6 " +
            "characters long and must contain at least one uppercase letter, one lower case letter, and one number. It must not be longer than 100 char.";

    @Transactional
    public String register(@Valid BlogUser blogUser, BindingResult result) {
        if (!blogUser.getPassword().matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,100}$")) {
            FieldError pwErr = new FieldError("blogUser", "password", PW_ERROR);
            result.addError(pwErr);
        }
        boolean exists = blogUserRepository.findByUsername(blogUser.getUsername()).isPresent();
        if (exists) {
            FieldError nameTaken = new FieldError("blogUser", "username", "Username is taken");
            result.addError(nameTaken);
        }
        if (!blogUser.getPassword().equals(blogUser.getPasswordRepeat())) {
            FieldError pwRepeat = new FieldError("blogUser", "passwordRepeat", "Please enter the same password");
            result.addError(pwRepeat);
        }
        if (result.hasErrors()) {
            return "register";
        }
        String encodePW = bCryptPasswordEncoder.encode(blogUser.getPassword());
        System.out.println(encodePW);
        blogUser.setPassword(encodePW);
        blogUserRepository.save(blogUser);
        return "login";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return blogUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}

package com.blog.day03blog.config;

import com.blog.day03blog.service.BlogUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final BlogUserService blogUserService;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/", "/contact").permitAll()
                    .antMatchers("/admin/**").hasRole("ADMIN")
                    .antMatchers("/reader/**").hasAnyRole("ADMIN", "READER")
                .and()
                .exceptionHandling()
                    .accessDeniedPage("/login")
                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login_err")
                    .defaultSuccessUrl("/")
                .and()
                .rememberMe()
                    .key("a-very-securty-key")
                    .userDetailsService(blogUserService)
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/");
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(bCryptPasswordEncoder);
        provider.setUserDetailsService(blogUserService);
        return provider;
    }
}

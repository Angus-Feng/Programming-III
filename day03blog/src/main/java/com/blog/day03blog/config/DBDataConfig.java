package com.blog.day03blog.config;

import com.blog.day03blog.entity.Article;
import com.blog.day03blog.entity.BlogUser;
import com.blog.day03blog.repo.ArticleRepository;
import com.blog.day03blog.repo.BlogUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@AllArgsConstructor
public class DBDataConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final static String ARTICLE = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
            "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a " +
            "galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, " +
            "but also the leap into electronic typesetting, remaining essentially unchanged.";

    @Bean
    CommandLineRunner addUsers(BlogUserRepository blogUserRepository, ArticleRepository articleRepository) {

        if(blogUserRepository.findFirstByUsernameIsNotNull().isEmpty() && articleRepository.findFirstByBodyIsNotNull().isEmpty()) {

            String password = bCryptPasswordEncoder.encode("Password123");
            return args -> {
                BlogUser jerry123 = new BlogUser(
                        "jerry123",
                        "jerry@gmail.com",
                        password
                );
                BlogUser terry123 = new BlogUser(
                        "terry123",
                        "terry@gmail.com",
                        password
                );
                BlogUser mary123 = new BlogUser(
                        "mary123",
                        "mary@gmail.com",
                        password
                );
                blogUserRepository.saveAll(
                        List.of(jerry123, terry123, mary123)
                );
                Article a1 = new Article(
                        jerry123,
                        LocalDateTime.now(),
                        "this is title",
                        ARTICLE
                );
                Article a2 = new Article(
                        jerry123,
                        LocalDateTime.now().plusHours(1),
                        "this is title",
                        ARTICLE
                );
                Article a3 = new Article(
                        terry123,
                        LocalDateTime.now().plusHours(2),
                        "this is title",
                        ARTICLE
                );
                Article a4 = new Article(
                        terry123,
                        LocalDateTime.now().plusHours(3),
                        "this is title",
                        ARTICLE
                );
                Article a5 = new Article(
                        mary123,
                        LocalDateTime.now().plusHours(4),
                        "this is title",
                        ARTICLE
                );
                Article a6 = new Article(
                        mary123,
                        LocalDateTime.now().plusHours(5),
                        "this is title",
                        ARTICLE
                );
                articleRepository.saveAll(
                        List.of(a1, a2, a3, a4, a5, a6)
                );
            };
        }
        return args -> {};
    }


}

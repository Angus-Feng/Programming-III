package com.blog.day03blog.service;

import com.blog.day03blog.entity.Article;
import com.blog.day03blog.entity.BlogUser;
import com.blog.day03blog.repo.ArticleRepository;
import com.blog.day03blog.repo.BlogUserRepository;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class BlogService {

    @Autowired
    private final ArticleRepository articleRepository;

    @Autowired
    private final BlogUserRepository blogUserRepository;

    public ModelAndView getAllArticles() {
        ModelAndView mav = new ModelAndView("index");
        List<Article> list = articleRepository.findTop10ByOrderByCreatedTSDesc();
        for (Article article : list) {
            String newBody = article.getBody().substring(0, Math.min(article.getBody().length(), 100)) + "...";
            article.setBody(newBody);
        }
        mav.addObject("articleList", list);
        return mav;
    }

    public ModelAndView getArticleById(Long articleId) {
        // TODO: article not found exception
        ModelAndView mav = new ModelAndView("article");
        Article article = articleRepository.findById(articleId).get();
        mav.addObject("article", article);
        return mav;
    }

    public ModelAndView showArticleForm() {
        ModelAndView mav = new ModelAndView("add_article");
        Article article = new Article();
        mav.addObject("article", article);
        return mav;
    }

    public String addArticle(@Valid Article article, BindingResult result, Principal principal) {
        // TODO: validation and error display
        if (result.hasErrors()) {
            return "add_article";
        }
        // TODO: author not found exception
        BlogUser author = blogUserRepository.findByUsername(principal.getName()).get();
        article.setAuthor(author);
        String cleanedArticle = Jsoup.clean(article.getBody(), Safelist.basic());
        article.setBody(cleanedArticle);
        article.setCreatedTS(LocalDateTime.now());
        Long id = articleRepository.save(article).getId();
        return "redirect:/article?id=" + id.toString();
    }
}

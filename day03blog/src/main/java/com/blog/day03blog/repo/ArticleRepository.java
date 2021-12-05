package com.blog.day03blog.repo;

import com.blog.day03blog.entity.Article;
import com.blog.day03blog.entity.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findTop10ByOrderByCreatedTSDesc();
}

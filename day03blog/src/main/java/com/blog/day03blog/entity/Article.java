package com.blog.day03blog.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.repository.Temporal;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Data
@NoArgsConstructor
public class Article {


    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private BlogUser author;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTS;

    private String title;

    @Length(max = 1000, min = 10)
    private String body;

    public Article(BlogUser author, LocalDateTime createdTS, String title, String body) {
        this.author = author;
        this.createdTS = createdTS;
        this.title = title;
        this.body = body;
    }

    public Article(BlogUser author, String title, String body) {
        this.author = author;
        this.title = title;
        this.body = body;
    }
}

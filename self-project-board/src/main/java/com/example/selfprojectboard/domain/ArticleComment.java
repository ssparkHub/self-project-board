package com.example.selfprojectboard.domain;

import java.time.LocalDateTime;

public class ArticleComment {

    private Long id;
    private Article article;
    private String title;
    private String content;
    private String hashtag;

    private LocalDateTime createAt;
    private String createdBy;
    private LocalDateTime modifiedAt;
    private String modifiedBy;


}

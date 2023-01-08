package com.example.selfprojectboard.repository;

import com.example.selfprojectboard.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;

@RepositoryRestResource
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
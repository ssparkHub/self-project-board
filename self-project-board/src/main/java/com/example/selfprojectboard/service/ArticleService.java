package com.example.selfprojectboard.service;

import com.example.selfprojectboard.domain.Article;
import com.example.selfprojectboard.domain.type.SearchType;
import com.example.selfprojectboard.dto.ArticleDto;
import com.example.selfprojectboard.dto.ArticleWithCommentsDto;
import com.example.selfprojectboard.repository.ArticleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticles(SearchType searchType, String searchKeyword, Pageable pageable) {
        if(searchKeyword == null || searchKeyword.isBlank()) {
            return articleRepository.findAll(pageable).map(ArticleDto::from);
        }

        return switch (searchType) {
            case TITLE -> articleRepository.findByTitleContaining(searchKeyword,pageable).map(ArticleDto::from);
            case CONTENT -> articleRepository.findByContentContaining(searchKeyword,pageable).map(ArticleDto::from);
            case ID -> articleRepository.findByUserAccount_UserIdContaining(searchKeyword,pageable).map(ArticleDto::from);
            case NICKNAME -> articleRepository.findByUserAccount_NicknameContaining(searchKeyword,pageable).map(ArticleDto::from);
            case HASHTAG -> articleRepository.findByHashtag("#" + searchKeyword,pageable).map(ArticleDto::from);
        };

    }
    @Transactional(readOnly = true)
    public ArticleWithCommentsDto getArticle(Long articleId) {
        return articleRepository.findById(articleId)
                .map(ArticleWithCommentsDto::from)
                .orElseThrow(() -> new EntityNotFoundException("게시글이 없습니다 - articleId: "+ articleId));
    }


    public void saveArticle(ArticleDto dto) {
        articleRepository.save(dto.toEntity());
    }

    public void updateArticle(ArticleDto dto) {
        try{

            Article article = articleRepository.getReferenceById(dto.id());
            if(dto.title() != null) { article.setTitle(dto.title());}
            if(dto.content() != null) {article.setContent(dto.content());}
            article.setHashtag(dto.hashtag());
            //@Transactional로 묶여있기때문에 영속성 변경을 감지하기 때문에 save가 따로 필요없다
        } catch (EntityNotFoundException e) {
            log.warn("게시판 업데이트 실패, 게시글을 찾을 수 없습니다 - dto: {}", dto);
            //인터폴레이션 기법: {} , T
            // "~~" + dto 로 작성할땐 로그를 찍지않아도 될때에도 dto에 메모리를 쓰지않는다
        }
    }
    public void deleteArticle(long articleId) {
        articleRepository.deleteById(articleId);

    }

    @Transactional(readOnly = true)
    public Page<ArticleDto> searchArticlesViaHashtag(String hashtag, Pageable pageable) {
        if(hashtag == null || hashtag.isBlank()) {
            return Page.empty(pageable);
        }

        return articleRepository.findByHashtag(hashtag, pageable).map(ArticleDto::from);
    }

    public List<String> getHashtags() {
        return articleRepository.findAllDistinctHashtags();
    }
}

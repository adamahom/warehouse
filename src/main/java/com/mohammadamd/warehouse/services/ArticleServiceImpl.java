package com.mohammadamd.warehouse.services;

import com.mohammadamd.warehouse.dtos.ImportBatchArticleDTO;
import com.mohammadamd.warehouse.entities.Article;
import com.mohammadamd.warehouse.repositories.ArticleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    private final ModelMapper modelMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
    }

    public void importArticlesBatch(ImportBatchArticleDTO batchArticles) {
        batchArticles.getArticles().forEach(article -> {
            Article articleEntity = modelMapper.map(article, Article.class);
            articleRepository.save(articleEntity);
        });
    }
}

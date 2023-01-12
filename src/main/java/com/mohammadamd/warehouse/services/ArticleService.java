package com.mohammadamd.warehouse.services;

import com.mohammadamd.warehouse.dtos.ImportBatchArticleDTO;
import com.mohammadamd.warehouse.entities.Article;

public interface ArticleService {

    public void importArticlesBatch(ImportBatchArticleDTO batchArticle);
}

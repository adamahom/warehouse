package com.mohammadamd.warehouse.repositories;

import com.mohammadamd.warehouse.entities.Article;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends CrudRepository<Article, Number> {
}

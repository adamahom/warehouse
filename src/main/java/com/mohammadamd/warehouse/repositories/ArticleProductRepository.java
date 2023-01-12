package com.mohammadamd.warehouse.repositories;

import com.mohammadamd.warehouse.entities.ArticleProduct;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleProductRepository extends CrudRepository<ArticleProduct, Number> {
}

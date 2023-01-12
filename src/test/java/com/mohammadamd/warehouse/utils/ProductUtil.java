package com.mohammadamd.warehouse.utils;

import com.mohammadamd.warehouse.entities.Article;
import com.mohammadamd.warehouse.entities.ArticleProduct;
import com.mohammadamd.warehouse.entities.Product;

import java.util.Set;

public class ProductUtil {
    public static Product generateAvailableTestProduct() {
        Article article = Article.builder().artId(1).name("test").stock(10).build();
        Article article2 = Article.builder().artId(2).name("test2").stock(2).build();
        Product product = Product.builder().prodId(1).name("test").build();
        ArticleProduct articleProduct = ArticleProduct.builder().product(product).article(article).amountOfArticle(1).build();
        ArticleProduct articleProduct2 = ArticleProduct.builder().product(product).article(article2).amountOfArticle(1).build();
        Set<ArticleProduct> setArticleProducts = Set.of(articleProduct, articleProduct2);
        product.setArticleProduct(setArticleProducts);
        return product;
    }

    public static Product generateNotAvailableTestProduct() {
        Article article = Article.builder().artId(1).name("test").stock(2).build();
        Article article2 = Article.builder().artId(2).name("test2").stock(2).build();
        Product product = Product.builder().prodId(1).name("test").build();
        ArticleProduct articleProduct = ArticleProduct.builder().product(product).article(article).amountOfArticle(10).build();
        ArticleProduct articleProduct2 = ArticleProduct.builder().product(product).article(article2).amountOfArticle(1).build();
        Set<ArticleProduct> setArticleProducts = Set.of(articleProduct, articleProduct2);
        product.setArticleProduct(setArticleProducts);
        return product;
    }
}

package com.mohammadamd.warehouse.services;

import com.mohammadamd.warehouse.dtos.ImportBatchProductDTO;
import com.mohammadamd.warehouse.entities.ArticleProduct;
import com.mohammadamd.warehouse.exceptions.ProductDoesNotExist;
import com.mohammadamd.warehouse.exceptions.ProductIsNotReadyToSale;
import com.mohammadamd.warehouse.exceptions.RequiredArticleForProductDoesNotExist;
import com.mohammadamd.warehouse.exceptions.ProductSoldOut;
import com.mohammadamd.warehouse.entities.Article;
import com.mohammadamd.warehouse.entities.Product;
import com.mohammadamd.warehouse.repositories.ArticleProductRepository;
import com.mohammadamd.warehouse.repositories.ArticleRepository;
import com.mohammadamd.warehouse.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ArticleRepository articleRepository;

    private final ArticleProductRepository articleProductRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              ArticleRepository articleRepository,
                              ArticleProductRepository articleProductRepository
    ) {
        this.productRepository = productRepository;
        this.articleRepository = articleRepository;
        this.articleProductRepository = articleProductRepository;
    }

    @Transactional
    public void buyProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(ProductDoesNotExist::new);
        if(product.getArticleProduct().size() == 0) {
            throw new ProductIsNotReadyToSale();
        }

        reduceNumberOfArticlesThatNeededForProduct(product);
    }

    private void reduceNumberOfArticlesThatNeededForProduct(Product product) {
        product.getArticleProduct().forEach(articleProduct -> {
            Article article = articleProduct.getArticle();
            if (article.getStock() < articleProduct.getAmountOfArticle()) {
                throw new ProductSoldOut();
            }
            article.setStock(articleProduct.getArticle().getStock() - articleProduct.getAmountOfArticle());
            articleRepository.save(article);
        });
    }

    @Override
    @Transactional
    public void importProductsBatch(ImportBatchProductDTO batchProduct) throws RequiredArticleForProductDoesNotExist {
        batchProduct.getProducts().forEach(productDTO -> {
            Product product = new Product();
            product.setName(productDTO.getName());
            product = productRepository.save(product);

            connectProductToArticles(productDTO.getContainArticles(), product);
        });
    }

    private void connectProductToArticles(List<ImportBatchProductDTO.containArticles> articles, Product product) {
        articles.forEach(containArticles -> {
            Article article = articleRepository.findById(containArticles.getArt_id()).orElseThrow(RequiredArticleForProductDoesNotExist::new);

            ArticleProduct articleProduct = new ArticleProduct();
            articleProduct.setProduct(product);
            articleProduct.setArticle(article);
            articleProduct.setAmountOfArticle(containArticles.getAmountOf());
            articleProductRepository.save(articleProduct);
        });
    }

    public Page<Product> getAllProducts(int page, int perPage) {
        return productRepository.findAll(PageRequest.of(page, perPage));
    }
}

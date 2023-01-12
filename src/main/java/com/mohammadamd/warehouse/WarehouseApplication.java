package com.mohammadamd.warehouse;

import com.mohammadamd.warehouse.repositories.ArticleProductRepository;
import com.mohammadamd.warehouse.repositories.ArticleRepository;
import com.mohammadamd.warehouse.repositories.ProductRepository;
import com.mohammadamd.warehouse.services.ArticleService;
import com.mohammadamd.warehouse.services.ArticleServiceImpl;
import com.mohammadamd.warehouse.services.ProductService;
import com.mohammadamd.warehouse.services.ProductServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public ArticleService articleService(ArticleRepository articleRepository, ModelMapper modelMapper) {
        return new ArticleServiceImpl(articleRepository, modelMapper);
    }

    @Bean
    public ProductService productService(ArticleRepository articleRepository,
                                         ProductRepository productRepository,
                                         ArticleProductRepository articleProductRepository
    ) {
        return new ProductServiceImpl(productRepository, articleRepository, articleProductRepository);
    }
}

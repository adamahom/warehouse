package com.mohammadamd.warehouse.controllers;

import com.mohammadamd.warehouse.WarehouseApplication;
import com.mohammadamd.warehouse.entities.Article;
import com.mohammadamd.warehouse.entities.Product;
import com.mohammadamd.warehouse.repositories.ArticleProductRepository;
import com.mohammadamd.warehouse.repositories.ArticleRepository;
import com.mohammadamd.warehouse.repositories.ProductRepository;
import com.mohammadamd.warehouse.utils.ProductUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {WarehouseApplication.class})
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ArticleProductRepository articleProductRepository;

    @Autowired
    private ArticleRepository articleRepository;


    @Test
    void whenBuyProductThatNotAvailable_thenReturnError() throws Exception {
        Product product = ProductUtil.generateNotAvailableTestProduct();
        product.setProdId(productRepository.save(product).getProdId());
        product.getArticleProduct().forEach(articleProduct -> {
            Article article = articleRepository.save(articleProduct.getArticle());
            articleProduct.setArticle(article);
            articleProductRepository.save(articleProduct);
        });

        mvc.perform(get("/api/products/buy?productId=" + product.getProdId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"message\":\"sorry! the product sold out :(.\"}"));
    }

    @Test
    public void givenProduct_whenGetProducts_thenReturnProduct()
            throws Exception {

        Product product = Product.builder().name("test").build();

        productRepository.save(product);
        mvc.perform(get("/api/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\"name\":\"test\", \"product_quantity\": 0}]"));
    }

    @Test
    void givenProduct_whenBuyProduct_thenReduceProductQuantity() throws Exception {
        Product product = ProductUtil.generateAvailableTestProduct();
         product.setProdId(productRepository.save(product).getProdId());
        product.getArticleProduct().forEach(articleProduct -> {
            Article article = articleRepository.save(articleProduct.getArticle());
            articleProduct.setArticle(article);
            articleProductRepository.save(articleProduct);
        });

        mvc.perform(get("/api/products/buy?productId=" + product.getProdId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\"message\":\"you have successfully bought product.\"}"));
    }


}

package com.mohammadamd.warehouse.services;

import com.mohammadamd.warehouse.entities.Product;
import com.mohammadamd.warehouse.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static com.mohammadamd.warehouse.utils.ProductUtil.generateAvailableTestProduct;

@SpringBootTest
public class ProductServiceTests {

    @Test
    public void whenGetAllProducts_thenReturnAllProducts() {
        ProductRepository productRepository = Mockito.mock(ProductRepository.class);
        ProductService productService = new ProductServiceImpl(productRepository, null, null);

        Product product = generateAvailableTestProduct();
        Page<Product> products = new PageImpl<>(List.of(product));
        Mockito.when(productRepository.findAll(PageRequest.of(0, 10))).thenReturn(products);

        List<Product> actual = productService.getAllProducts(0, 10).toList();

        List<Product> getProductsDTOList = List.of(product);
        Assertions.assertEquals(getProductsDTOList, actual);
    }
}

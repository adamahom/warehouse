package com.mohammadamd.warehouse.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mohammadamd.warehouse.dtos.ImportBatchArticleDTO;
import com.mohammadamd.warehouse.dtos.ImportBatchProductDTO;
import com.mohammadamd.warehouse.exceptions.RequiredArticleForProductDoesNotExist;
import com.mohammadamd.warehouse.services.ArticleService;
import com.mohammadamd.warehouse.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class AdminController {

    private final ProductService productService;

    private final ArticleService articleService;

    public AdminController(ProductService productService, ArticleService articleService) {
        this.productService = productService;
        this.articleService = articleService;
    }

    @PostMapping("api/admin/import-articles-batch")
    public ResponseEntity<Object> importArticlesBatch(@RequestParam("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        ImportBatchArticleDTO importBatchArticleDTO;

        try {
            importBatchArticleDTO = objectMapper.readValue(file.getBytes(), ImportBatchArticleDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"Message\":\"invalid json file.\"}");
        }

        articleService.importArticlesBatch(importBatchArticleDTO);

        return ResponseEntity.ok("{\"Message\":\"articles imported successfully!\"}");
    }

    @PostMapping("api/admin/import-products-batch")
    public ResponseEntity<Object> importProductsBatch(@RequestParam("file") MultipartFile file) {
        ObjectMapper objectMapper = new ObjectMapper();
        ImportBatchProductDTO importBatchProductDTO;

        try {
            importBatchProductDTO = objectMapper.readValue(file.getBytes(), ImportBatchProductDTO.class);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"Message\":\"Invalid json file.\"}");
        }

        try {
            productService.importProductsBatch(importBatchProductDTO);
        }

        catch (RequiredArticleForProductDoesNotExist e) {
            return ResponseEntity.unprocessableEntity().body("{\"Message\":\"one or more required articles for product does not exist.\"}");
        }

        catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"Message\":\"could not import products batch.\"}");
        }

        return ResponseEntity.ok("{\"Message\":\"products imported successfully!\"}");
    }
}

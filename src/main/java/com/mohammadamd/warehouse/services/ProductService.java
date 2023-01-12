package com.mohammadamd.warehouse.services;

import com.mohammadamd.warehouse.dtos.ImportBatchProductDTO;
import com.mohammadamd.warehouse.entities.Product;
import com.mohammadamd.warehouse.exceptions.ProductDoesNotExist;
import com.mohammadamd.warehouse.exceptions.ProductIsNotReadyToSale;
import com.mohammadamd.warehouse.exceptions.ProductSoldOut;
import com.mohammadamd.warehouse.exceptions.RequiredArticleForProductDoesNotExist;
import org.springframework.data.domain.Page;

public interface ProductService {

    public Page<Product> getAllProducts(int page, int perPage);

    public void buyProduct(int id) throws ProductSoldOut, ProductDoesNotExist, ProductIsNotReadyToSale;

    public void importProductsBatch(ImportBatchProductDTO batchProduct) throws RequiredArticleForProductDoesNotExist;
}

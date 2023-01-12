package com.mohammadamd.warehouse.repositories;

import com.mohammadamd.warehouse.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Number>, CrudRepository<Product, Number> {
}

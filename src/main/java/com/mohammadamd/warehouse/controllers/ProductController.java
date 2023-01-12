package com.mohammadamd.warehouse.controllers;

import com.mohammadamd.warehouse.dtos.GetProductsDTO;
import com.mohammadamd.warehouse.entities.Product;
import com.mohammadamd.warehouse.exceptions.ProductDoesNotExist;
import com.mohammadamd.warehouse.exceptions.ProductIsNotReadyToSale;
import com.mohammadamd.warehouse.exceptions.ProductSoldOut;
import com.mohammadamd.warehouse.services.ProductService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductService productService;

    private final ModelMapper modelMapper;

    public ProductController(
            ProductService productService,
            ModelMapper modelMapper
    ) {
        this.productService = productService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "api/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GetProductsDTO>> getAllProducts(@RequestParam(defaultValue = "0") int page) {
        return ResponseEntity.ok(
                productService.getAllProducts(page, 10).stream().map(product -> {
                    GetProductsDTO productDto = modelMapper.map(product, GetProductsDTO.class);
                    productDto.setProductQty(product.getProductQty());
                    return productDto;
                }
        ).collect(Collectors.toList()));
    }

    @GetMapping(value = "api/products/buy", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> buyProduct(@RequestParam int productId) {

        try {
            productService.buyProduct(productId);
        }

        catch (ProductSoldOut e) {
            return ResponseEntity.badRequest().body("{\"message\":\"sorry! the product sold out :(.\"}");
        }

        catch (ProductDoesNotExist e) {
            return ResponseEntity.badRequest().body("{\"message\":\"product does not exist.\"}");
        }

        catch (ProductIsNotReadyToSale e) {
            return ResponseEntity.badRequest().body("{\"message\":\"sorry! the product is not ready to sale :(.\"}");
        }

        catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"message\":\"something went wrong.\"}");
        }

        return ResponseEntity.ok("{\"message\":\"you have successfully bought product.\"}");
    }
}

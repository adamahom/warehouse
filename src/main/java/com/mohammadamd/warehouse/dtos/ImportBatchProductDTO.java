package com.mohammadamd.warehouse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor @Builder
public class ImportBatchProductDTO {

    @JsonProperty("products")
    private List<Product> products;

    @Getter
    public static class Product {

        @JsonProperty("name")
        private String name;

        @JsonProperty("contain_articles")
        private List<containArticles> containArticles;
    }

    @Getter
    public static class  containArticles {

        @JsonProperty("art_id")
        private int art_id;

        @JsonProperty("amount_of")
        private int amountOf;
    }
}

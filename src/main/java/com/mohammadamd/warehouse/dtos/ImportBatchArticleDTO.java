package com.mohammadamd.warehouse.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter @AllArgsConstructor @Builder
public class ImportBatchArticleDTO {

    @JsonProperty("inventory")
    private List<Article> articles;

    @Getter
    public static class Article {

        @JsonProperty("art_id")
        private String artId;

        @JsonProperty("name")
        private String name;

        @JsonProperty("stock")
        private int stock;

    }
}

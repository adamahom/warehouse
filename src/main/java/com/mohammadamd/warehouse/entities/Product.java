package com.mohammadamd.warehouse.entities;

import lombok.*;

import javax.persistence.*;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "products")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int prodId;

    @Column(nullable = false, length = 200)
    private String name;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ArticleProduct> articleProduct = new HashSet<>();

    public int getProductQty() {
        if (this.articleProduct.size() == 0) {
            return 0;
        }

        return this.articleProduct.stream().reduce(-1, (minQty, articleProduct) -> {
            if (articleProduct.getArticle() == null) {
                return 0;
            }

            if (minQty == -1) {
                return articleProduct.getArticle().getStock() / articleProduct.getAmountOfArticle();
            }

            int currentQty = articleProduct.getArticle().getStock() / articleProduct.getAmountOfArticle();
            if (currentQty < minQty) {
                return currentQty;
            }

            return minQty;
        }, Integer::min);
    }
}

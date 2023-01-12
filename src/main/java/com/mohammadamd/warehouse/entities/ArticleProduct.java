package com.mohammadamd.warehouse.entities;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "articles_products", uniqueConstraints = @UniqueConstraint(columnNames = {"art_id", "prod_id"}))
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ArticleProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "art_id")
    private Article article;
    @ManyToOne
    @JoinColumn(name = "prod_id")
    private Product product;
    @Column(nullable = false)
    private int amountOfArticle;
}

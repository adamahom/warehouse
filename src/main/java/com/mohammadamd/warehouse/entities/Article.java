package com.mohammadamd.warehouse.entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "articles")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @Id
    private int artId;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false)
    private int stock;

    @OneToMany(mappedBy = "article")
    private Set<ArticleProduct> articleProduct = new HashSet<>();
}

package com.example.salesIntel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Setter
@Getter
public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    private Float goal;

    @OneToMany(mappedBy = "establishment")
    private List<User> user;

    @OneToMany(mappedBy = "establishment")
    private List<Product> products;
}

package com.example.salesIntel.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Float purchasePrice;
    
    @Column(nullable = false)
    private Float salePrice;

    @Column(nullable = false)
    private Integer quantity;
    
    @Column(nullable = false)
    private String unit;
    
    @Column(nullable = false)
    private Date expiration;
    
    @Column(nullable = false)
    private Integer batch;

    @CreatedDate
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @JoinColumn(nullable = false)
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<SalesProducts> salesProducts;

    @ManyToOne
    private User user;
}

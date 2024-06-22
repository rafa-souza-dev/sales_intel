package com.example.salesIntel.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @JoinColumn(nullable = false)
    @ManyToOne
    private Category category;

    @OneToMany(mappedBy = "product")
    private List<Sale> sales;

    @ManyToOne
    private User user;
}

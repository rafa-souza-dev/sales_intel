package com.example.salesIntel.repository;

import com.example.salesIntel.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    public List<Product> getAllByUserId(Long userId);
}

package com.example.salesIntel.repository;

import com.example.salesIntel.model.SalesProducts;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalesProductsRepository extends JpaRepository<SalesProducts, Long> {
}

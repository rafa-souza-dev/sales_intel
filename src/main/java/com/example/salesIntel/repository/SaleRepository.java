package com.example.salesIntel.repository;

import com.example.salesIntel.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query("SELECT sale FROM Sale sale WHERE sale.product.user.id = :userId")
    public List<Sale> getAllByUserId(Long userId);
}

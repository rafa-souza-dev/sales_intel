package com.example.salesIntel.repository;

import com.example.salesIntel.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    public List<Sale> getAllByUserId(Long userId);
}

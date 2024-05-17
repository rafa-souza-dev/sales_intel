package com.example.salesIntel.model.dtos;

import java.util.Date;

import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ProductDTO {
	
	@Size(max = 255, min = 3)
    private String name;

    private Float purchasePrice;
    
    private Float salePrice;

    private Integer quantity;
    
    private String unit;
    
    private Date expiration;
    
    private Integer batch;

    private Long categoryId;

    private Long userId;
}

package com.example.salesIntel.controller.responses;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {
	
    private Long id;

    private String name;

    private Float purchasePrice;
    
    private Float salePrice;

    private Integer quantity;
    
    private String unit;
    
    private Date expiration;
    
    private Integer batch;

    private Date createdAt;

    private Date updatedAt;

    private Long categoryId;

    private List<SalesProductsResponse> salesProducts;

    private Long userId;

}

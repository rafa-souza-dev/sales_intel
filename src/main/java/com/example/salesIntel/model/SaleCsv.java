package com.example.salesIntel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SaleCsv {
    private float value;

    private int quantity;

    private Date createdAt;

    private String productName;

    public SaleCsv(Sale sale) {
        this.value = sale.getValue();
        this.quantity = sale.getQuantity();
        this.createdAt = sale.getCreatedAt();
        this.productName = sale.getProduct().getName();
    }
}

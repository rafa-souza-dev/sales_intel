package com.example.salesIntel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SaleVO {

    private String product;

    private Integer salesQuantity;

    private Float revenue;

    private Float cost;

    private Float balance;

    private Integer stock;

    public SaleVO(Product product) {
        this.product = product.getName();
        this.salesQuantity = product.getSales().stream().map(Sale::getQuantity).reduce(0, Integer::sum);
        this.revenue = product.getSales().stream().map(Sale::getValue).reduce(0.0f, Float::sum);
        this.cost = this.salesQuantity * product.getPurchasePrice();
        this.balance = this.revenue - this.cost;
        this.stock = product.getQuantity();
    }

    public SaleVO(List<SaleVO> saleVOs) {
        this.product = "Total";
        this.salesQuantity = saleVOs.stream().map(SaleVO::getSalesQuantity).reduce(0, Integer::sum);
        this.revenue = saleVOs.stream().map(SaleVO::getRevenue).reduce(0.0f, Float::sum);
        this.cost = saleVOs.stream().map(SaleVO::getCost).reduce(0.0f, Float::sum);
        this.balance = saleVOs.stream().map(SaleVO::getBalance).reduce(0.0f, Float::sum);
    }
}

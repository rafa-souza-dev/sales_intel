package com.example.salesIntel.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FinancialVO {

    private String date;

    private float revenue;

    private float cost;

    private float balance;

    private float percentageBalance;

    public FinancialVO(String date, List<Sale> sales) {
        this.date = date;
        this.revenue = sales.stream().map(Sale::getValue).reduce(0.0F, Float::sum);
        this.cost = sales.stream().map(Sale::getProduct).map(Product::getPurchasePrice).reduce(0.0F, Float::sum);
        this.balance = this.revenue - this.cost;
        this.percentageBalance = this.balance/this.revenue;
    }
}

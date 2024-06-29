package com.example.salesIntel.controller.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;



@Getter
@Setter
public class SaleResponse {

    private float value;

    private int quantity;

    private Date createdAt;

    private Date updatedAt;

    private ProductResponse product;

}

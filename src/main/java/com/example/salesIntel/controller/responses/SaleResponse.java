package com.example.salesIntel.controller.responses;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
public class SaleResponse {

    private float value;

    private Date createdAt;

    private Date updatedAt;

    private List<SalesProductsResponse> salesProducts;

}

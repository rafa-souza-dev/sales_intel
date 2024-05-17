package com.example.salesIntel.controller.responses;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {

    private Integer id;

    private String name;

    private List<ProductResponse> products;

}

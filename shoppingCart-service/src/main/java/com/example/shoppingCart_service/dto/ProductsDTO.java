package com.example.shoppingCart_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {
    private Long productId;
    private String nameProduct;
    private Double price;
    private Integer stock;
    private String category;
    private Integer quantity;
}

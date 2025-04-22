package com.example.shoppingCart_service.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class CartItem {

    private Long productId;
    private String nameProduct;
    private String category;
    private Integer quantity;
    private Double price;

}

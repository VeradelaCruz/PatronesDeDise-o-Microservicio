package com.example.catalog_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Products {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long productId;
    @NotBlank(message = "This space can not be on blank. ")
    private String nameProduct;
    @NotBlank(message = "This space can not be on blank. ")
    private String category;
    private Double price;
    private Integer stock;

}

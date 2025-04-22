package com.example.shoppingCart_service.models;

import com.example.shoppingCart_service.dto.ProductsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "catalog-service")
public interface ProductClient {

    @GetMapping("/catalog/getById/{productId}")
    ProductsDTO getProductById(@PathVariable("productId") Long productId);

    @PutMapping("/catalog/updateProduct/{productId}")
    ProductsDTO updateProduct(@PathVariable("productId") Long id, @RequestBody ProductsDTO updatedProduct);

}



package com.example.catalog_service.service;

import com.example.catalog_service.models.Products;
import com.example.catalog_service.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    //Show all products:
    public List<Products> showAllProducts(){
        return productsRepository.findAll();
    }

    //Show product by id:
    public Products showProductById(Long productId){
        return productsRepository.findById(productId).
                orElseThrow(()->new RuntimeException("Product not found."));
    }
    //Create product:
    public List<Products> createProduct(List<Products> productsList){
        return productsRepository.saveAll(productsList);
    }

    //Update product:
    public Products changeProduct(Products updatedProduct){
        Products existingProduct= productsRepository.findById(updatedProduct.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + updatedProduct.getProductId()));

            existingProduct.setNameProduct(updatedProduct.getNameProduct());
            existingProduct.setCategory(updatedProduct.getCategory());
            existingProduct.setStock(updatedProduct.getStock());
            existingProduct.setPrice(updatedProduct.getPrice());

            return productsRepository.save(existingProduct);
    }

    //Delete product:
    public String removeProduct(Long productId) {
        Products removedProduct = productsRepository.findById(productId)
                .orElseThrow(()-> new RuntimeException("Product not found."));
        productsRepository.deleteById(removedProduct.getProductId());
        return removedProduct.getNameProduct() + " has been removed.";
    }


}

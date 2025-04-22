package com.example.catalog_service.controller;

import com.example.catalog_service.models.Products;
import com.example.catalog_service.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/catalog")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    //Get all products:
    @GetMapping("/getAllProducts")
    public List<Products> getAllProducts(){
        return productsService.showAllProducts();
    }

    //Get product by id:
    @GetMapping("/getById/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable Long productId){
        try {
            Products product= productsService.showProductById(productId);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody List<Products> products){
        try {
            productsService.createProduct(products);
            return ResponseEntity.ok("Product/s added.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Long productId,@RequestBody Products product){
        try {
            product.setProductId(productId);
            Products updatedProduct= productsService.changeProduct(product);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long productId){
        try {
            productsService.removeProduct(productId);
            return ResponseEntity.ok("Product removed");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

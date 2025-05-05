package com.example.shoppingCart_service.controller;

import com.example.shoppingCart_service.dto.CartDTO;
import com.example.shoppingCart_service.models.Cart;
import com.example.shoppingCart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    public CartService cartService;

    @GetMapping("/getAllCarts")
    public List<Cart> getAllCarts() {
        return cartService.showAllICarts();
    }

    @GetMapping("/getCartById/{cartId}")
    public ResponseEntity<?> getCartById(@PathVariable Long cartId){
        try {
            Cart cart =cartService.showCartsById(cartId);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
    }

    @DeleteMapping("/deleteCart/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId){
        try {
            cartService.removeCart(cartId);
            return ResponseEntity.ok("Cart removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/createCart")
    public CompletableFuture<ResponseEntity<Object>> createCart(@RequestBody CartDTO cartDTO) {
        return cartService.createCart(cartDTO)
                .thenApply(cart -> ResponseEntity.status(HttpStatus.CREATED).body(cart))
                .exceptionally(ex -> {
                    Throwable cause = ex.getCause();
                    if (cause instanceof RuntimeException) {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(cause.getMessage());
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body("An error occurred: " + cause.getMessage());
                    }
                });
    }



}

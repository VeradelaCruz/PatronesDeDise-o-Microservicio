package com.example.shoppingCart_service.controller;

import com.example.shoppingCart_service.dto.CartDTO;
import com.example.shoppingCart_service.models.Cart;
import com.example.shoppingCart_service.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/deleteCart")
    public ResponseEntity<?> deleteCart(@PathVariable Long cartId){
        try {
            cartService.removeCart(cartId);
            return ResponseEntity.ok("Cart removed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/createCart")
    public ResponseEntity<?> createCart(@RequestBody CartDTO cartDTO) {
        try {
            // Llamamos al servicio que gestiona la lógica de creación de carrito
            Cart cart = cartService.createCart(cartDTO);
            // Retornamos una respuesta exitosa con el carrito creado
            return ResponseEntity.status(HttpStatus.CREATED).body(cart);
        } catch (RuntimeException e) {
            // En caso de que no haya suficiente stock o algún otro error, lo capturamos y lo respondemos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            // Para otros errores generales
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }
    }
}

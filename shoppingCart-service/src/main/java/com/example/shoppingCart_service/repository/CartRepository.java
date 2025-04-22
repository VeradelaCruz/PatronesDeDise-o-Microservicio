package com.example.shoppingCart_service.repository;

import com.example.shoppingCart_service.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}

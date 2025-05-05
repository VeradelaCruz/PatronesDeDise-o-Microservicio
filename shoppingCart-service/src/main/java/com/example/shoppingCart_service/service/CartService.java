package com.example.shoppingCart_service.service;

import com.example.shoppingCart_service.dto.CartDTO;
import com.example.shoppingCart_service.dto.CartItemDTO;
import com.example.shoppingCart_service.dto.ProductsDTO;
import com.example.shoppingCart_service.models.Cart;
import com.example.shoppingCart_service.models.CartItem;
import com.example.shoppingCart_service.models.ProductClient;
import com.example.shoppingCart_service.repository.CartRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class CartService {
    @Autowired
    public CartRepository cartRepository;

    @Autowired
    public ProductClient productClient;

    //show all items in the cart
    public List<Cart> showAllICarts(){
        return cartRepository.findAll();
    }

    //show item by id:
    public Cart showCartsById(Long cartId){
        return cartRepository.findById(cartId)
                .orElseThrow(()->new RuntimeException("Cart not found"));
    }

    // delete a cart:
    public String removeCart(Long cartId){
       cartRepository.deleteById(cartId);
       return "Cart with id: "+ cartId + " removed.";
    }

    //create cart with dto
    @CircuitBreaker(name = "catalog-service", fallbackMethod = "fallbackCreateCart")
    @Retry(name = "catalog-service")
    @TimeLimiter(name = "catalog-service")
    public CompletableFuture<Object> createCart(CartDTO cartDTO) {
        return CompletableFuture.supplyAsync(() -> {
            List<CartItem> validatedItems = new ArrayList<>();
            Double totalPrice = 0.0;

            for (CartItemDTO item : cartDTO.getItems()) {
                ProductsDTO productDTO = productClient.getProductById(item.getProductId());

                if (item.getQuantity() > productDTO.getStock()) {
                    throw new RuntimeException("The product " + productDTO.getNameProduct() + " has not enough stock");
                }

                // Reducir el stock
                productDTO.setStock(productDTO.getStock() - item.getQuantity());
                productClient.updateProduct(productDTO.getProductId(), productDTO);

                CartItem cartItem = new CartItem();
                cartItem.setProductId(productDTO.getProductId());
                cartItem.setNameProduct(productDTO.getNameProduct());
                cartItem.setPrice(productDTO.getPrice());
                cartItem.setCategory(productDTO.getCategory());
                cartItem.setQuantity(item.getQuantity());

                validatedItems.add(cartItem);

                totalPrice += productDTO.getPrice() * item.getQuantity();
            }

            Cart cart = new Cart();
            cart.setItems(validatedItems);
            cart.setTotalPrice(totalPrice);

            return cartRepository.save(cart);
        });
    }

    public CompletableFuture<Object> fallbackCreateCart(CartDTO cartDTO, Throwable throwable) {
        ProductsDTO fallbackProduct = new ProductsDTO();
        fallbackProduct.setProductId(-1L);
        fallbackProduct.setNameProduct("Not available");
        fallbackProduct.setStock(0);
        fallbackProduct.setPrice(0.0);
        fallbackProduct.setQuantity(0);
        fallbackProduct.setCategory("None");
        return CompletableFuture.completedFuture(fallbackProduct);
    }
}




package com.example.shoppingCart_service.service;

import com.example.shoppingCart_service.dto.CartDTO;
import com.example.shoppingCart_service.dto.CartItemDTO;
import com.example.shoppingCart_service.dto.ProductsDTO;
import com.example.shoppingCart_service.models.Cart;
import com.example.shoppingCart_service.models.CartItem;
import com.example.shoppingCart_service.models.ProductClient;
import com.example.shoppingCart_service.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public Cart createCart(CartDTO cartDTO){
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
    }



}

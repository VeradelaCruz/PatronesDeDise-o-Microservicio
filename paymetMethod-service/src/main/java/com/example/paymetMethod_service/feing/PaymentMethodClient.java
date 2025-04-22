package com.example.paymetMethod_service.feing;

import com.example.paymetMethod_service.dto.CartDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "shoppingCart-service")
public interface PaymentMethodClient {
    //Get shoppingCart
    @GetMapping("/cart/getCartById/{cartId}")
    CartDTO getCartById(@PathVariable("cartId") Long cartId);
}

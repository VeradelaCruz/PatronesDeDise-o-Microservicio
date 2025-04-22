package com.example.purchaseManagment_service.feing;

import com.example.purchaseManagment_service.dto.CartDTO;
import com.example.purchaseManagment_service.dto.PaymentDTO;
import com.example.purchaseManagment_service.dto.PaymentMethodDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "paymentMethod-service")
public interface PaymentMethodClient {
    @GetMapping("/payment/getPaymentById/{paymentId}")
    PaymentMethodDTO getPaymentById(@PathVariable("paymentId") Long paymentMethodId);

    @PostMapping("/payment/payCart")
    PaymentDTO payCart(@RequestBody PaymentDTO paymentDTO);



}

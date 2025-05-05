package com.example.purchaseManagment_service.controller;

import com.example.purchaseManagment_service.dto.PaymentDTO;
import com.example.purchaseManagment_service.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    public PurchaseService purchaseService;


    @PostMapping("/paidPurchases")
    public CompletableFuture<ResponseEntity<?>> paidPurchases(@RequestBody PaymentDTO paymentDTO){
        return purchaseService.registerPayment(paymentDTO)
                .<ResponseEntity<?>>thenApply(payment -> ResponseEntity.status(HttpStatus.CREATED).body(payment))
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

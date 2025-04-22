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

@RestController
@RequestMapping("/purchase")
public class PurchaseController {
    @Autowired
    public PurchaseService purchaseService;


    @PostMapping("/paidPurchases")
    public ResponseEntity<?> paidPurchases(@RequestBody PaymentDTO paymentDTO){
        try {
            purchaseService.registerPayment(paymentDTO);
            return ResponseEntity.ok(paymentDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

}

package com.example.paymetMethod_service.controller;

import com.example.paymetMethod_service.dto.CartDTO;
import com.example.paymetMethod_service.models.Payment;
import com.example.paymetMethod_service.models.PaymentMethod;
import com.example.paymetMethod_service.service.PaymentMethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/paymentMethod")
public class PaymentMethodController {
    @Autowired
    public PaymentMethodService paymentMethodService;

    @GetMapping("/getAllMethods")
    public ResponseEntity<?> getAllPayments(){
        try {
            List<PaymentMethod> payments= paymentMethodService.findAllMethods();
            return ResponseEntity.ok(payments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getById/{paymentMethodId}")
    private ResponseEntity<?> getPaymentById(@PathVariable Long paymentMethodId){
        try {
            PaymentMethod foundedPayment= paymentMethodService.findPaymentById(paymentMethodId);
            return ResponseEntity.ok(foundedPayment);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPayment(@RequestBody List<PaymentMethod> payment){
        try {
            paymentMethodService.createPayment(payment);
            return ResponseEntity.ok("Payment/s saved.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @DeleteMapping("/remove/{paymentMethodId}")
    public ResponseEntity<?> removePayment(@PathVariable Long paymentMethodId){
        try {
            paymentMethodService.removePayment(paymentMethodId);
            return ResponseEntity.ok("Payment removed.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


}

package com.example.paymetMethod_service.controller;

import com.example.paymetMethod_service.dto.CartDTO;
import com.example.paymetMethod_service.models.Payment;
import com.example.paymetMethod_service.models.PaymentMethod;
import com.example.paymetMethod_service.models.PaymentRequest;
import com.example.paymetMethod_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    public PaymentService paymentService;

    @GetMapping("/getAllPayments")
    public ResponseEntity<?> getAllPayments(){
        try {
            List<Payment> payments= paymentService.findAllPayments();
            return ResponseEntity.ok(payments);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/getPaymentById/{paymentId}")
    private ResponseEntity<?> getPaymentById(@PathVariable Long paymentId){
        try {
            Payment foundedPayment= paymentService.findPaymentById(paymentId);
            return ResponseEntity.ok(foundedPayment);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment not found");
        }
    }

    @GetMapping("/getPaymentByCartId/{cartId}")
    private ResponseEntity<?> getPaymentByCartId(@PathVariable Long cartId){
        try {
            Payment foundedPaymentCart= paymentService.findPaymentByCartId(cartId);
            return ResponseEntity.ok(foundedPaymentCart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cart not found");
        }
    }


    @PostMapping("/payCart")
    public CompletableFuture<ResponseEntity<?>> payCartShopping(@RequestBody @Valid PaymentRequest paymentRequest) {
        return paymentService.payCart(paymentRequest.getCartDTO(), paymentRequest.getPaymentMethod())
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



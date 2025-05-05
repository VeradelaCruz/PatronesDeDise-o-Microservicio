package com.example.purchaseManagment_service.service;

import com.example.purchaseManagment_service.dto.CartDTO;
import com.example.purchaseManagment_service.dto.PaymentDTO;
import com.example.purchaseManagment_service.enums.State;
import com.example.purchaseManagment_service.feing.CartClient;
import com.example.purchaseManagment_service.feing.PaymentMethodClient;
import com.example.purchaseManagment_service.models.Purchase;
import com.example.purchaseManagment_service.repository.PurchaseRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.CompletableFuture;

@Service
public class PurchaseService {
    @Autowired
    public PurchaseRepository purchaseRepository;

    @Autowired
    public  CartClient cartClient;

    @Autowired
    public PaymentMethodClient paymentMethodClient;


    @CircuitBreaker(name = "purchaseManagment-service", fallbackMethod = "fallbackRegisterPayment")
    @Retry(name = "purchaseManagment-service")
    @TimeLimiter(name = "purchaseManagment-service")
    //Registramos la compra del carrito:
    public CompletableFuture<Purchase> registerPayment(@RequestBody PaymentDTO paymentDTO){
        return CompletableFuture.supplyAsync(()->{
        //Traer todos los datos del carrito:
        CartDTO cartAmount= cartClient.getCartById(paymentDTO.getCartDTO().getCartId());

        //Traer la compra del carrito:
        PaymentDTO paymentDTO1 = paymentMethodClient.getPaymentByCartId(paymentDTO.getCartDTO().getCartId());

        //Asignar los valores  a los atributos de la clase Purchase:
        Purchase payment = new Purchase();
        payment.setPaymentMethod(paymentDTO1.getPaymentMethod().getMethodName());
        payment.setPaymentState(State.APPROVED);
        payment.setTotalAmount(cartAmount.getTotalPrice());

        return purchaseRepository.save(payment);
        });
    }

    //Metodo fallback
    public CompletableFuture<Purchase> fallbackRegisterPayment(PaymentDTO paymentDTO,Throwable throwable){
        Purchase payment = new Purchase();
        payment.setPaymentMethod("UNKNOWN");
        payment.setPaymentState(State.FAILED);
        payment.setTotalAmount(0.0);

        System.out.print("Error system "+ throwable.getMessage());

        return CompletableFuture.completedFuture(payment);
    }

}

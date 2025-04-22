package com.example.paymetMethod_service.service;

import com.example.paymetMethod_service.dto.CartDTO;
import com.example.paymetMethod_service.enums.PaymentState;
import com.example.paymetMethod_service.feing.PaymentMethodClient;
import com.example.paymetMethod_service.models.Payment;
import com.example.paymetMethod_service.models.PaymentMethod;
import com.example.paymetMethod_service.repository.PaymentMethodRepository;
import com.example.paymetMethod_service.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {
    @Autowired
    public PaymentRepository paymentRepository;

    @Autowired
    public PaymentMethodClient paymentMethodClient;

    @Autowired
    public PaymentMethodService paymentMethodService;

    @Autowired
    public PaymentMethodRepository paymentMethodRepository;

    //Get all payments
    public List<Payment> findAllPayments(){
        return paymentRepository.findAll();
    }

    //Get payment by id
    public Payment findPaymentById(Long paymentId){
        return paymentRepository.findById(paymentId)
                .orElseThrow(()-> new RuntimeException("Payment not found"));
    }

    //Get payment by card id:
    public  Payment findPaymentByCartId(Long cartId){
        return paymentRepository.findPaymentByCartId(cartId)
                .orElseThrow(() -> new RuntimeException("Payment not found for this cart"));
    }

    //Create payment
    public Payment payCart(CartDTO cartDTO, PaymentMethod paymentMethod) {
        CartDTO cart = paymentMethodClient.getCartById(cartDTO.getCartId());

        Payment payment= new Payment();
        payment.setPaymentState(PaymentState.PENDING);
        //Verifico que el carrito existe:
        if (cart == null) {
            throw new RuntimeException("Cart not found with id: " + cartDTO.getCartId());
        }
        //Verifico que el monto no es cero:
        if (cart.getTotalPrice()<=0) {
            throw new RuntimeException("Cart amount is invalid. Please check the total price.");
        }
        // Verifico que el metodo de pago es valido:
        PaymentMethod paymentClient= paymentMethodService.
                findPaymentById(paymentMethod.getPaymentMethodId());
        if (paymentClient == null) {
            throw new RuntimeException("Payment method not found.");
        }

        //Busco el payment id para evitar duplicarse:
        PaymentMethod paymentMethod1= paymentMethodRepository.findById(paymentMethod.getPaymentMethodId())
                        .orElseThrow(() -> new RuntimeException("PaymentMethod not found"));


        payment.setPaymentState(PaymentState.APPROVED);
        payment.setCartId(cartDTO.getCartId());
        payment.setTotalAmount(cart.getTotalPrice());
        payment.setPaymentMethod(paymentMethod1);
        payment.setPaymentDate(LocalDateTime.now());

        return paymentRepository.save(payment);

    }

}

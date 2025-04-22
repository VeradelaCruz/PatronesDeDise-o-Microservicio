package com.example.paymetMethod_service.service;

import com.example.paymetMethod_service.models.Payment;
import com.example.paymetMethod_service.models.PaymentMethod;
import com.example.paymetMethod_service.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    @Autowired
    public PaymentMethodRepository paymentMethodRepository;

    //Get all payments
    public List<PaymentMethod> findAllMethods(){
        return paymentMethodRepository.findAll();
    }

    //Get payment by id
    public PaymentMethod findPaymentById(Long paymentMethodId){
        return paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(()-> new RuntimeException("Payment not found"));
    }
    //Add payment method
    public List<PaymentMethod> createPayment(List<PaymentMethod> payments){
        return paymentMethodRepository.saveAll(payments);
    }



    //Remove payment
    public String removePayment(Long paymentMethodId){
        PaymentMethod existingPayment= paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(()->new RuntimeException("Payment not found"));
        paymentMethodRepository.deleteById(existingPayment.getPaymentMethodId());
        return existingPayment.getMethodName() + " has been removed.";

    }
}

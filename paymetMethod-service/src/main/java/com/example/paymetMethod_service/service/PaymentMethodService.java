package com.example.paymetMethod_service.service;
import com.example.paymetMethod_service.models.PaymentMethod;
import com.example.paymetMethod_service.repository.PaymentMethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        List<PaymentMethod> newPayments = new ArrayList<>();
        for (PaymentMethod paymentMethod : payments) {
            if (paymentMethodRepository.existsByMethodNameAndProvider(paymentMethod.getMethodName(), paymentMethod.getProvider())) {
                throw new RuntimeException("Payment method already exists with method name: " + paymentMethod.getMethodName() + " and provider: " + paymentMethod.getProvider());
            }
            newPayments.add(paymentMethodRepository.save(paymentMethod));
        }
        return newPayments;
    }




    //Remove payment
    public String removePayment(Long paymentMethodId){
        PaymentMethod existingPayment= paymentMethodRepository.findById(paymentMethodId)
                .orElseThrow(()->new RuntimeException("Payment not found"));
        paymentMethodRepository.deleteById(existingPayment.getPaymentMethodId());
        return existingPayment.getMethodName() + " has been removed.";

    }
}

package com.example.purchaseManagment_service.service;

import com.example.purchaseManagment_service.dto.CartDTO;
import com.example.purchaseManagment_service.dto.PaymentDTO;
import com.example.purchaseManagment_service.enums.State;
import com.example.purchaseManagment_service.feing.CartClient;
import com.example.purchaseManagment_service.feing.PaymentMethodClient;
import com.example.purchaseManagment_service.models.Purchase;
import com.example.purchaseManagment_service.repository.PurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class PurchaseService {
    @Autowired
    public PurchaseRepository purchaseRepository;

    @Autowired
    public  CartClient cartClient;

    @Autowired
    public PaymentMethodClient paymentMethodClient;

    //Registramos la compra del carrito:
    public Purchase registerPayment(@RequestBody PaymentDTO paymentDTO){
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

    }

}

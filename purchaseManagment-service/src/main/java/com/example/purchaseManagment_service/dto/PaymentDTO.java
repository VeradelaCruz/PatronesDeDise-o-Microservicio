package com.example.purchaseManagment_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private CartDTO cartDTO;
    private PaymentMethodDTO paymentMethod;
}

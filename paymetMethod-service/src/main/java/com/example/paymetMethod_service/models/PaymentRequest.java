package com.example.paymetMethod_service.models;

import com.example.paymetMethod_service.dto.CartDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    @NotNull
    CartDTO cartDTO;
    @NotNull
    PaymentMethod paymentMethod;
}

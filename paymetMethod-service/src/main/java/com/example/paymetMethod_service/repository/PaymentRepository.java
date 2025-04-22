package com.example.paymetMethod_service.repository;

import com.example.paymetMethod_service.dto.CartDTO;
import com.example.paymetMethod_service.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findPaymentByCartId(Long cartId);
}

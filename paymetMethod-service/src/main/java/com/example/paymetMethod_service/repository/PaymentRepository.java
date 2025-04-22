package com.example.paymetMethod_service.repository;

import com.example.paymetMethod_service.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}

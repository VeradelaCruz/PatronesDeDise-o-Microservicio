package com.example.paymetMethod_service.repository;

import com.example.paymetMethod_service.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>{
}

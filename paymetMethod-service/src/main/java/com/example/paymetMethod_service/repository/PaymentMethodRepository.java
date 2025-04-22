package com.example.paymetMethod_service.repository;

import com.example.paymetMethod_service.models.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Long>{
    boolean existsByMethodNameAndProvider(String methodName, String provider);
}

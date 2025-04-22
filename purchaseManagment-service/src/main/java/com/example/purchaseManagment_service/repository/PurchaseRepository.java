package com.example.purchaseManagment_service.repository;

import com.example.purchaseManagment_service.models.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
}

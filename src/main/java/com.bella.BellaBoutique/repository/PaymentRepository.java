package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, String> {
    List<Payment> findByOrderId(Long orderId);
    List<Payment> findByStatus(String status);
} 
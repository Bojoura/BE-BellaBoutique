package com.bella.BellaBoutique.model.payment;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "payments")
public class Payment {
    @Id
    private String id;
    
    @Column(name = "order_id")
    private Long orderId;
    
    private String status;
    private double total;
    private String detail;
} 
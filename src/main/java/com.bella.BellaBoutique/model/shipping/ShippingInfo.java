package com.bella.BellaBoutique.model.shipping;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "shipping_info")
public class ShippingInfo {
    @Id
    private String id;
    
    @Column(name = "shipping_cost")
    private double shippingCost;
    
    @Column(name = "shipping_type")
    private String shippingType;
    
    @Column(name = "shipping_address")
    private String shippingAddress;
} 
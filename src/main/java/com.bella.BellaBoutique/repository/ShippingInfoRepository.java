package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.shipping.ShippingInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingInfoRepository extends JpaRepository<ShippingInfo, String> {
} 
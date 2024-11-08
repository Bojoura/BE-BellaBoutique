package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
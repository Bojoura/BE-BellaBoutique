package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p FROM Product p WHERE p.rating > :minRating AND p.stock > :minStock ORDER BY p.rating DESC")
    List<Product> findByRatingGreaterThanAndStockGreaterThanOrderByRatingDesc(
        @Param("minRating") Double minRating,
        @Param("minStock") Integer minStock
    );
}
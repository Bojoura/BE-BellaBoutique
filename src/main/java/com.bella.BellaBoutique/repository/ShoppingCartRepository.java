package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.cart.ShoppingCart;
import com.bella.BellaBoutique.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findByUser(User user);
    Optional<ShoppingCart> findByUserId(Long userId);
    void deleteByUserId(Long userId);
} 
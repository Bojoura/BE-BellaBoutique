package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
        Optional<User> findByEmail(String email);
        Optional<User> findById(Long id);
}
package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.users.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileUploadRepository extends JpaRepository<UserPhoto, String> {
    Optional<UserPhoto> findByFileName(String fileName);
}

package com.bella.BellaBoutique.repository;

import com.bella.BellaBoutique.model.users.UserPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileUploadRepository extends JpaRepository<UserPhoto, Long> {
    Optional<UserPhoto> findByFileName(String fileName);

}

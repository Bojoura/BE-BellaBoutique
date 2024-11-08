package com.bella.BellaBoutique.service;

import com.bella.BellaBoutique.model.users.UserPhoto;
import com.bella.BellaBoutique.repository.FileUploadRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class PhotoService {

    private final Path fileStoragePath;
    private final FileUploadRepository repo;

    public PhotoService(@Value("${my.upload_location}") String fileStorageLocation, FileUploadRepository repo) throws IOException {
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.repo = repo;
        if (!Files.exists(this.fileStoragePath)) {
            Files.createDirectory(this.fileStoragePath);
        }
    }

    public UserPhoto storeFile(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        if (fileName.contains("..")) {
            throw new IOException("Ongeldige bestandsnaam: " + fileName);
        }

        Path targetLocation = fileStoragePath.resolve(fileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        UserPhoto photo = new UserPhoto(fileName);
        repo.save(photo);
        return photo;
    }

    public Resource downLoadFile(String fileName) {
        try {
            Path filePath = fileStoragePath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Bestand niet beschikbaar of niet leesbaar: " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Fout bij het laden van het bestand: " + fileName, e);
        }
    }
}

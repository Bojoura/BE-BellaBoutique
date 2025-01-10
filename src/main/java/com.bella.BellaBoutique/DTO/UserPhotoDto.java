package com.bella.BellaBoutique.DTO;

import com.bella.BellaBoutique.model.users.UserPhoto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPhotoDto {

    private Long id;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Photo URL is mandatory")
    @Pattern(regexp = "^/images/.*$", message = "Photo URL must start with '/images/'")
    private String photoUrl;

    @NotBlank(message = "File name is mandatory")
    @Size(max = 255, message = "File name must not exceed 255 characters")
    private String fileName;

    @NotBlank(message = "File type is mandatory")
    @Pattern(regexp = "^(image\\/)(jpeg|png|gif)$", message = "File type must be a valid image type (jpeg, png, gif)")
    private String fileType;

    public UserPhotoDto(UserPhoto userPhoto) {
        this.id = userPhoto.getId();
        this.fileName = userPhoto.getFileName();
        this.fileType = userPhoto.getFileType();
        this.photoUrl = "/images/" + userPhoto.getFileName();
    }
}


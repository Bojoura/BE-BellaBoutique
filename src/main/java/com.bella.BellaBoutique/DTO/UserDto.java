package com.bella.BellaBoutique.DTO;

import com.bella.BellaBoutique.model.users.UserPhoto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    @NotBlank(message = "Username is mandatory")
    public String username;

    @NotBlank(message = "Password is mandatory")
    public String password;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    public String email;

    public Set<String> authority;

    public String photoUrl;

//    UserPhoto photo;
}

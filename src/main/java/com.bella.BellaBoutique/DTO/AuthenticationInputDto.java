package com.bella.BellaBoutique.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationInputDto {

    @NotBlank(message = "Email is vereist")
    private String email;

    @NotBlank(message = "Password is vereist")
    private String password;
}

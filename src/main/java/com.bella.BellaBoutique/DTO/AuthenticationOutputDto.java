package com.bella.BellaBoutique.DTO;

public class AuthenticationOutputDto {

    private final String jwt;

    public AuthenticationOutputDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

}

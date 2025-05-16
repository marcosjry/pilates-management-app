package com.user.managament.DTO.user;


import jakarta.validation.constraints.NotBlank;

public record UserLoginReqDTO(

        @NotBlank(message = "Usuario não pode estar em branco.")
        String userName,

        @NotBlank(message = "Senha não pode estar em branco.")
        String password) {

}

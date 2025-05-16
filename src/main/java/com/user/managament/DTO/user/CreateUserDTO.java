package com.user.managament.DTO.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(
        @Size(min = 5, max = 100, message = "Digite o nome completo.")
        @NotBlank(message = "Nome não pode estar em branco.")
        String name,

        @Size(min = 7, max = 100, message = "O Usuario deve ter no mínimo 7 caracteres")
        @NotBlank(message = "Usuario não pode estar em branco.")
        String userName,

        @Size(min = 8, max = 100, message = "A senha deve ter no mínimo 8 caracteres")
        @NotBlank(message = "Senha não pode estar em branco.")
        String password
) { }

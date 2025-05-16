package com.user.managament.DTO.customer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CustomerToCreateDTO(
        @Size(min = 5, max = 100, message = "Digite o nome completo.")
        @NotBlank(message = "Nome não pode estar em branco.")
        @NotNull(message = "Nome não pode ser NULL.")
        String name,

        @Size(min = 11, max = 11, message = "CPF deve ter 11 dígitos")
        @NotBlank(message = "CPF não pode estar em branco.")
        @NotNull(message = "CPF não pode ser NULL.")
        String cpf,

        @Size(min = 11, max = 11, message = "O Celular deve ter 11 caracteres")
        @NotBlank(message = "Celular não pode estar em branco.")
        @NotNull(message = "Celular não pode ser NULL.")
        String phone,

        @Size(min = 10, max = 50, message = "O Email deve ter no mínimo 8 caracteres")
        @NotBlank(message = "Email não pode estar em branco.")
        @NotNull(message = "Email não pode ser NULL.")
        String email,


        @NotBlank(message = "Tipo de Aula não pode estar em branco.")
        @NotNull(message = "Tipo de Aula não pode ser NULL.")
        String classroomType

) { }

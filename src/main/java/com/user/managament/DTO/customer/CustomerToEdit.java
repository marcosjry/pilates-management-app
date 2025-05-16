package com.user.managament.DTO.customer;

public record CustomerToEdit(
        String name,
        String cpf,
        String email,
        String phone,
        String classroomType
) { }

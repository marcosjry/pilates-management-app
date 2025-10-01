package com.user.managament.customer.dto;

public record CustomerToEdit(
        String name,
        String cpf,
        String email,
        String phone,
        String classroomType
) { }

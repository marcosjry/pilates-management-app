package com.user.managament.DTO.customer;

import com.user.managament.model.classroom.ClassroomType;

import java.util.UUID;

public record CustomersFrequencyClassDTO(
        UUID id,
        String name,
        ClassroomType classroomType
) {
}

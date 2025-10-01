package com.user.managament.customer.dto;

import com.user.managament.shared.model.classroom.ClassroomType;

import java.util.UUID;

public record CustomersFrequencyClassDTO(
        UUID id,
        String name,
        ClassroomType classroomType
) {
}

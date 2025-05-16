package com.user.managament.DTO.customer;

import java.util.UUID;

public record CustomersFrequencyClassDTO(
        UUID id,
        String name,
        String classroomType
) {
}

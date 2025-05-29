package com.user.managament.DTO.classroom;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public record FrequencyBatchDTO(
        LocalDate date,
        UUID classroomId,
        List<UUID> customers
) {
}

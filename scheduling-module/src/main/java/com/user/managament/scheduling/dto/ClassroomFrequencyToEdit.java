package com.user.managament.scheduling.dto;

import java.time.LocalDate;
import java.util.UUID;

public record ClassroomFrequencyToEdit(UUID classroomId, UUID customerId, LocalDate classDate) {
}

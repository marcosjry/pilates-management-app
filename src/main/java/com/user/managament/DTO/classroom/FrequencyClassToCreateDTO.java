package com.user.managament.DTO.classroom;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record FrequencyClassToCreateDTO(

        @NotNull(message = "A Aula não pode ser NULL.")
        UUID classroomId,

        @NotNull(message = "O Aluno não pode ser NULL.")
        UUID customerId,

        @NotNull(message = "A data não pode ser NULL.")
        LocalDate classDate

        ) {
}
package com.user.managament.customer.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record FrequencyByDateAndHourDTO(

        @NotNull(message = "Data não pode ser null")
        LocalDate date,

        @NotNull(message = "Hora não pode ser null")
        LocalTime hour) {
}

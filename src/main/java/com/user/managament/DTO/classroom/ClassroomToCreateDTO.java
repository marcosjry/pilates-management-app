package com.user.managament.DTO.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;


public record ClassroomToCreateDTO(

        @NotBlank(message = "O tipo de Aula não pode estar em branco.")
        @NotNull(message = "O tipo de Aula não pode ser NULL.")
        String classroomType,

        @NotNull(message = "A hora não pode ser NULL.")
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime

) { }

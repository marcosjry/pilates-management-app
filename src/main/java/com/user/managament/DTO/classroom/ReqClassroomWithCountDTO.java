package com.user.managament.DTO.classroom;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ReqClassroomWithCountDTO(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) { }

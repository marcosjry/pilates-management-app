package com.user.managament.scheduling.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record ReqClassroomWithCountDTO(
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate date
) { }

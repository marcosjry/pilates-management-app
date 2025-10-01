package com.user.managament.scheduling.dto;

import com.user.managament.shared.model.classroom.ClassroomType;

import java.time.LocalTime;
import java.util.UUID;

public record ClassroomHoursAvailable(
        UUID classroomID,
        ClassroomType classroomType,
        LocalTime startTime
) {
}

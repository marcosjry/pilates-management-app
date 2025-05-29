package com.user.managament.DTO.classroom;

import com.user.managament.model.classroom.ClassroomType;

import java.time.LocalTime;
import java.util.UUID;

public record ClassroomHoursAvailable(
        UUID classroomID,
        ClassroomType classroomType,
        LocalTime startTime
) {
}

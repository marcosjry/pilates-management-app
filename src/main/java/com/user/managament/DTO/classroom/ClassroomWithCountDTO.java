package com.user.managament.DTO.classroom;

import com.user.managament.model.classroom.ClassroomType;

import java.time.LocalTime;
import java.util.UUID;

public record ClassroomWithCountDTO(
        UUID classroomId,
        LocalTime startTime,
        ClassroomType classroomType,
        Long studentCount
) {
}

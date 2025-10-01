package com.user.managament.scheduling.dto;

import com.user.managament.shared.model.classroom.ClassroomType;

import java.time.LocalTime;
import java.util.UUID;

public record ClassroomWithCountDTO(
        UUID classroomId,
        LocalTime startTime,
        ClassroomType classroomType,
        Long studentCount
) {
}

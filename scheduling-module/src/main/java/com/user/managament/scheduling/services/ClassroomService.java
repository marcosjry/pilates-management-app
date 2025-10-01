package com.user.managament.scheduling.services;

import com.user.managament.scheduling.dto.ClassroomHoursAvailable;
import com.user.managament.scheduling.dto.ClassroomToCreateDTO;
import com.user.managament.scheduling.dto.ClassroomWithCountDTO;
import com.user.managament.scheduling.exception.ClassroomDoesntExistsException;
import com.user.managament.scheduling.model.Classroom;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClassroomService {

    void doesCreateClassroom(ClassroomToCreateDTO classroomToCreateDTO);
    void doesDeleteClassroom(UUID classroomId);

    Classroom findClassroomById(UUID classroomId) throws ClassroomDoesntExistsException;

    List<ClassroomWithCountDTO> findClassroomsWithStudents(LocalDate date);
    void deleteClassroomById(UUID classroomId);

    List<ClassroomHoursAvailable> findClassesHoursAvailable();

}

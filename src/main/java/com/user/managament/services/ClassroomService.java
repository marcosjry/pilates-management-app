package com.user.managament.services;

import com.user.managament.DTO.classroom.ClassroomHoursAvailable;
import com.user.managament.DTO.classroom.ClassroomToCreateDTO;
import com.user.managament.DTO.classroom.ClassroomWithCountDTO;
import com.user.managament.DTO.classroom.ReqClassroomWithCountDTO;
import com.user.managament.exception.ClassroomDoesntExistsException;
import com.user.managament.model.classroom.Classroom;

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

package com.user.managament.services.impl;

import com.user.managament.DTO.classroom.ClassroomToCreateDTO;
import com.user.managament.DTO.classroom.ClassroomWithCountDTO;
import com.user.managament.exception.ClassroomDoesntExistsException;
import com.user.managament.exception.UserDoesntExistException;
import com.user.managament.model.classroom.Classroom;
import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.user.User;
import com.user.managament.repository.ClassroomRepository;
import com.user.managament.services.ClassroomService;
import com.user.managament.services.UserService;
import com.user.managament.util.SharedUtilClass;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassroomRepository classroomRepository;

    @Override
    public void doesCreateClassroom(ClassroomToCreateDTO classroomToCreateDTO) {
        try {
            createClassroom(classroomToCreateDTO);
        } catch (UserDoesntExistException e) {
            throw new UserDoesntExistException(e.getMessage());
        }
    }

    @Override
    public void deleteClassroomById(UUID classroomId) {
        this.classroomRepository.deleteById(classroomId);
    }

    @Transactional
    public void createClassroom(ClassroomToCreateDTO classroomToCreateDTO) throws UserDoesntExistException {
        String userName = SharedUtilClass.captura();
        User user = this.userService.findUserByUserName(userName);

        ClassroomType type = ClassroomType.fromString(classroomToCreateDTO.classroomType());

        Classroom classroom = new Classroom(
                type,
                user,
                classroomToCreateDTO.startTime()
        );

        this.classroomRepository.save(classroom);
    }

    @Transactional
    public void deleteClassroom(UUID classroomId) throws ClassroomDoesntExistsException {
        boolean existsById = this.classroomRepository.existsById(classroomId);
        if(!existsById)
            throw new ClassroomDoesntExistsException("Turma não existe.");

        this.classroomRepository.deleteById(classroomId);
    }

    @Override
    public void doesDeleteClassroom(UUID classroomId) {
        try {
            deleteClassroom(classroomId);
        } catch ( ClassroomDoesntExistsException e ) {
            throw new ClassroomDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public Classroom findClassroomById(UUID classroomId) throws ClassroomDoesntExistsException {
        return this.classroomRepository.findById(classroomId).orElseThrow(() -> new ClassroomDoesntExistsException("Classroom doesnt found."));
    }

    @Override
    public List<ClassroomWithCountDTO> findClassroomsWithStudents(LocalDate date) {
        return this.classroomRepository.findClassroomsWithStudentCountByDate(date);
    }


}

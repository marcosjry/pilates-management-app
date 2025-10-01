package com.user.managament.scheduling.services.impl;

import com.user.managament.customer.exception.CustomerDoesntExistsException;
import com.user.managament.customer.model.Customer;
import com.user.managament.customer.services.CustomerService;
import com.user.managament.iam.exception.UserDoesntExistException;
import com.user.managament.iam.model.User;
import com.user.managament.iam.services.UserService;
import com.user.managament.scheduling.dto.ClassroomHoursAvailable;
import com.user.managament.scheduling.dto.ClassroomToCreateDTO;
import com.user.managament.scheduling.dto.ClassroomWithCountDTO;
import com.user.managament.scheduling.exception.ClassroomDoesntExistsException;
import com.user.managament.scheduling.model.Classroom;
import com.user.managament.scheduling.repository.ClassroomRepository;
import com.user.managament.scheduling.services.ClassroomService;
import com.user.managament.shared.model.classroom.ClassroomType;
import com.user.managament.shared.util.SharedUtilClass;
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

    @Override
    public List<ClassroomHoursAvailable> findClassesHoursAvailable() {
        return this.classroomRepository.findClassesHoursAvailable();
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

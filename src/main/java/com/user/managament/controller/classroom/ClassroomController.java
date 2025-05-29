package com.user.managament.controller;

import com.user.managament.DTO.classroom.ClassroomToCreateDTO;
import com.user.managament.DTO.classroom.ClassroomWithCountDTO;
import com.user.managament.config.EndPointsAPI;
import com.user.managament.services.ClassroomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = EndPointsAPI.CLASSROOM)
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createClassroom(@Valid @RequestBody ClassroomToCreateDTO classroomToCreateDTO) {
        classroomService.doesCreateClassroom(classroomToCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Classroom Successfully created."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable UUID classroomId) {
        classroomService.doesDeleteClassroom(classroomId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Deleted Successfully."));
    }

    @GetMapping("/today-classes")
    public ResponseEntity<List<ClassroomWithCountDTO>> getClassWithCount() {
        List<ClassroomWithCountDTO> classWithCount =  classroomService.findClassroomsWithStudents(LocalDate.now());
        return ResponseEntity.status(HttpStatus.OK).body(classWithCount);
    }


}

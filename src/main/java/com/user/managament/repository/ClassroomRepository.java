package com.user.managament.repository;

import com.user.managament.DTO.classroom.ClassroomHoursAvailable;
import com.user.managament.DTO.classroom.ClassroomWithCountDTO;
import com.user.managament.model.classroom.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

    @Query("""
    SELECT new com.user.managament.DTO.classroom.ClassroomWithCountDTO(
        c.id,
        c.startTime,
        c.classroomType,
        COUNT(cf.customer)
    )
    FROM ClassroomFrequency cf
    JOIN cf.classroom c
    WHERE cf.classroomDate = :date
    GROUP BY c.id, c.startTime, c.classroomType
    """)
    List<ClassroomWithCountDTO> findClassroomsWithStudentCountByDate(@Param("date") LocalDate date);

    @Query("""
    SELECT new com.user.managament.DTO.classroom.ClassroomHoursAvailable(
        c.id,
        c.classroomType,
        c.startTime
    )
    FROM Classroom c
    ORDER BY c.startTime ASC
    """)
    List<ClassroomHoursAvailable> findClassesHoursAvailable();

}

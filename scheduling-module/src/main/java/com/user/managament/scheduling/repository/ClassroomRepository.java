package com.user.managament.scheduling.repository;

import com.user.managament.scheduling.dto.ClassroomHoursAvailable;
import com.user.managament.scheduling.dto.ClassroomWithCountDTO;
import com.user.managament.scheduling.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClassroomRepository extends JpaRepository<Classroom, UUID> {

    @Query("""
    SELECT new com.user.managament.scheduling.dto.ClassroomWithCountDTO(
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
    SELECT new com.user.managament.scheduling.dto.ClassroomHoursAvailable(
        c.id,
        c.classroomType,
        c.startTime
    )
    FROM Classroom c
    ORDER BY c.startTime ASC
    """)
    List<ClassroomHoursAvailable> findClassesHoursAvailable();

}

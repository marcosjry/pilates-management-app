package com.user.managament.repository;

import com.user.managament.model.classroom.ClassroomFrequency;
import com.user.managament.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface FrequencyClassRepository extends JpaRepository<ClassroomFrequency, UUID> {

    @Query("""
    SELECT cf FROM ClassroomFrequency cf
    WHERE cf.classroomDate = :date
    """)
    List<Customer> findFrequenciesByDate(
            @Param("date") LocalDate date
    );

    @Query("""
    SELECT c
    FROM ClassroomFrequency fr
    JOIN fr.customer c
    JOIN fr.classroom cl
    WHERE fr.classroomDate = :dateFilter
      AND cl.startTime = :timeFilter
    """)
    List<Customer> findCustomersByDateAndTime(
            @Param("dateFilter") LocalDate dateFilter,
            @Param("timeFilter") LocalTime timeFilter
    );

    boolean existsByCustomerIdAndClassroomIdAndClassroomDateAndClassroom_StartTime(
            UUID customerId,
            UUID classroomId,
            LocalDate classroomDate,
            LocalTime startTime
    );

    void deleteByCustomerIdAndClassroomId(UUID customerId, UUID classroomId);
}

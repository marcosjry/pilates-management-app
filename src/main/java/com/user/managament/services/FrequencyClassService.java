package com.user.managament.services;

import com.user.managament.DTO.classroom.FrequencyBatchDTO;
import com.user.managament.DTO.classroom.FrequencyClassToCreateDTO;
import com.user.managament.DTO.customer.CustomersFrequencyClassDTO;
import com.user.managament.DTO.customer.FrequencyByDateAndHourDTO;
import com.user.managament.DTO.customer.CustomerDTO;
import com.user.managament.model.customer.Customer;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface FrequencyClassService {

    void doesCreateFrequencyClassroom(FrequencyClassToCreateDTO frequencyClassToCreateDTO);
    void doesDeleteFrequencyClassroom(UUID frequencyId);

    void doesDeleteByCustomerIdAndClassroomId(UUID customerId, UUID classroomId);

    void createFrequencyByBatch(FrequencyBatchDTO toCreate);

    List<Customer> getCustomersByFrequencyClass(LocalDate dateFilter, LocalTime timeFilter);
    List<CustomersFrequencyClassDTO> doesGetCustomersByFrequencyClass(LocalDate date, LocalTime hour);
}

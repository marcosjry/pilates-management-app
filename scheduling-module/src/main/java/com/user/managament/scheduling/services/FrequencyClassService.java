package com.user.managament.scheduling.services;

import com.user.managament.customer.dto.CustomersFrequencyClassDTO;
import com.user.managament.customer.model.Customer;
import com.user.managament.scheduling.dto.FrequencyBatchDTO;
import com.user.managament.scheduling.dto.FrequencyClassToCreateDTO;

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

package com.user.managament.services;

import com.user.managament.DTO.classroom.FrequencyClassToCreateDTO;
import com.user.managament.DTO.customer.CustomersFrequencyClassDTO;
import com.user.managament.DTO.customer.FrequencyByDateAndHourDTO;
import com.user.managament.DTO.customer.CustomerDTO;
import com.user.managament.model.customer.Customer;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface FrequencyClassService {

    void doesCreateFrequencyClassroom(FrequencyClassToCreateDTO frequencyClassToCreateDTO);
    void doesDeleteFrequencyClassroom(UUID frequencyId);

    List<Customer> getCustomersByFrequencyClass(LocalDate dateFilter, LocalTime timeFilter);
    List<CustomersFrequencyClassDTO> doesGetCustomersByFrequencyClass(FrequencyByDateAndHourDTO frequencyByDateAndHourDTO);
}

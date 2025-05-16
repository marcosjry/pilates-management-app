package com.user.managament.controller;

import com.user.managament.DTO.classroom.FrequencyClassToCreateDTO;
import com.user.managament.DTO.customer.CustomersFrequencyClassDTO;
import com.user.managament.DTO.customer.FrequencyByDateAndHourDTO;
import com.user.managament.DTO.customer.CustomerDTO;
import com.user.managament.config.EndPointsAPI;
import com.user.managament.services.FrequencyClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = EndPointsAPI.FREQUENCY)
public class FrequencyClassController {

    @Autowired
    private FrequencyClassService frequencyClassService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createClassroom(@Valid @RequestBody FrequencyClassToCreateDTO frequencyClassToCreateDTO) {
        frequencyClassService.doesCreateFrequencyClassroom(frequencyClassToCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "FrequencyClassroom Successfully created."));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable UUID frequencyClassId) {
        frequencyClassService.doesDeleteFrequencyClassroom(frequencyClassId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Frequency Successfully Deleted."));
    }

    @GetMapping
    public ResponseEntity<List<CustomersFrequencyClassDTO>> getCustomersByDateAndHour(@RequestBody FrequencyByDateAndHourDTO frequencyByDateAndHourDTO) {
        List<CustomersFrequencyClassDTO> customerDTOS = frequencyClassService.doesGetCustomersByFrequencyClass(frequencyByDateAndHourDTO);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOS);
    }

}

package com.user.managament.controller;

import com.user.managament.DTO.classroom.FrequencyBatchDTO;
import com.user.managament.DTO.classroom.FrequencyClassToCreateDTO;
import com.user.managament.DTO.customer.CustomersFrequencyClassDTO;
import com.user.managament.config.EndPointsAPI;
import com.user.managament.services.FrequencyClassService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @GetMapping("/search")
    public ResponseEntity<List<CustomersFrequencyClassDTO>> getCustomersByDateAndHour(
            @RequestParam LocalDate date,
            @RequestParam LocalTime hour
    ) {
        List<CustomersFrequencyClassDTO> customerDTOS = frequencyClassService.doesGetCustomersByFrequencyClass(date, hour);
        return ResponseEntity.status(HttpStatus.OK).body(customerDTOS);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Map<String, String>> deleteFrequencyByCustomerIdAndClassroomId(
            @RequestParam UUID customerId,
            @RequestParam UUID classroomId
    ) {
        frequencyClassService.doesDeleteByCustomerIdAndClassroomId(customerId, classroomId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Frequência removida com sucesso." ));
    }

    @PostMapping("/batch-create")
    public ResponseEntity<Map<String, String>> createFrequencyByBatch(@RequestBody FrequencyBatchDTO frequencyBatchDTO) {
        frequencyClassService.createFrequencyByBatch(frequencyBatchDTO);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "Frequências salvas com sucesso." ));
    }

}

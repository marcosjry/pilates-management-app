package com.user.managament.controller;

import com.user.managament.DTO.customer.CustomersContractStatusDTO;
import com.user.managament.DTO.customer.CustomerDTO;
import com.user.managament.DTO.customer.CustomerToCreateDTO;
import com.user.managament.DTO.customer.CustomerToEdit;
import com.user.managament.config.EndPointsAPI;
import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.contract.PaymentType;
import com.user.managament.services.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = EndPointsAPI.CUSTOMER)
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createCustomer(@Valid @RequestBody CustomerToCreateDTO customerToCreateDTO) {
        customerService.doesCreateCustomer(customerToCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Customer created!"));
    }

    @PutMapping("/{customerId}")
    public ResponseEntity<Map<String, String>> editCustomer(@PathVariable UUID customerId, @RequestBody CustomerToEdit customerToEdit) {
        customerService.doesEditCustomer(customerId,customerToEdit);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem", "Customer edited successfully."));
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Map<String, String>> deleteCustomer(@PathVariable UUID customerId) throws Exception {
        customerService.doesDeleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("mensagem",  "Customer deleted."));
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Map<String, CustomerDTO>> getCustomer(@PathVariable UUID customerId) {
        CustomerDTO customerDTO = customerService.doesGetCustomer(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("cliente", customerDTO));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<CustomersContractStatusDTO>> getCustomersAndLastContractsByFilter(
            @RequestParam(required = false) ClassroomType roomType,
            @RequestParam(required = false) ContractStatus status,
            @RequestParam(required = false) PaymentType pType,
            @RequestParam(required = false) String name
    ) {
        List<CustomersContractStatusDTO> page = customerService.searchCustomersAndLastContractsByFilter(roomType, status, pType, name);
        return ResponseEntity.status(HttpStatus.OK).body(page);
    }
}

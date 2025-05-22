package com.user.managament.controller;

import com.user.managament.DTO.contract.*;
import com.user.managament.config.EndPointsAPI;
import com.user.managament.services.ContractService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(path = EndPointsAPI.CONTRACT)
public class ContractController {

    @Autowired
    private ContractService contractService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createContract(@Valid @RequestBody ContractToCreateDTO contractToCreateDTO) {
        contractService.doesCreateContract(contractToCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Contract created."));
    }

    @PutMapping("/{contractId}")
    public ResponseEntity<Map<String, String>> editContract(@PathVariable UUID contractId, @RequestBody ContractToEditDTO contractToEditDTO) {
        contractService.doesEditContract(contractId,contractToEditDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Contract Edited Successfully. "));
    }

    @DeleteMapping("/{contractId}")
    public ResponseEntity<Map<String, String>> deleteContract(@PathVariable UUID contractId) {
        contractService.doesDeleteContract(contractId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "Contract deleted successfully"));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContractsAndCustomerDTO>> getContract(@RequestParam String status) {
        List<ContractsAndCustomerDTO> dto = contractService.getContracts(status);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<Map<String, List<ContractDTO>>> getContractsFromCustomer(@PathVariable UUID customerId) {
        List<ContractDTO> contractDTOList = contractService.getContractsFromCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("resposta", contractDTOList));
    }

    @GetMapping("/last/{customerId}")
    public ResponseEntity<Map<String, List<ContractDTO>>> getLastContractFromCustomer(@PathVariable UUID customerId) {
        List<ContractDTO> contractDTOList = contractService.findLastContractByCustomerId(customerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("resposta", contractDTOList));
    }

    @GetMapping("/totals")
    public ResponseEntity<ActiveContractsWithCustomersDTO> getTotalActiveContractsAndClients() {
        ActiveContractsWithCustomersDTO dto = contractService.getTotalActiveContractsAndClients();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @GetMapping("/expiring-contracts")
    public ResponseEntity<List<ContractsExpiring>> getExpiringContractsWithClients() {
        List<ContractsExpiring> dto = contractService.findExpiringContracts();
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

}

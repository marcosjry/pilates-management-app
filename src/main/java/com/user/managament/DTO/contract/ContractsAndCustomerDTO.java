package com.user.managament.DTO.contract;

import com.user.managament.model.contract.ContractStatus;

import java.time.LocalDate;
import java.util.UUID;

public record ContractsAndCustomerDTO(
        UUID customerId,
        String customerName,
        UUID contractId,
        LocalDate initDate,
        LocalDate endDate,
        ContractStatus contractStatus
) {
}

package com.user.managament.DTO.contract;

import com.user.managament.model.contract.ContractStatus;

import java.time.LocalDate;
import java.util.UUID;

public record ContractsExpiring(
        UUID customerId,
        String customerName,
        LocalDate expiresAt,
        ContractStatus contractStatus
) {
}
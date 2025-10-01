package com.user.managament.contract.dto;

import com.user.managament.shared.model.contract.ContractStatus;

import java.time.LocalDate;
import java.util.UUID;

public record ContractsExpiring(
        UUID customerId,
        String customerName,
        LocalDate expiresAt,
        ContractStatus contractStatus
) {
}

package com.user.managament.contract.dto;

import com.user.managament.shared.model.contract.ContractStatus;
import com.user.managament.shared.model.contract.PaymentType;

import java.time.LocalDate;
import java.util.UUID;

public record ContractsAndCustomerDTO(
        UUID customerId,
        String customerName,
        PaymentType paymentType,
        UUID contractId,
        LocalDate initDate,
        LocalDate endDate,
        Double price,
        ContractStatus contractStatus
) {
}

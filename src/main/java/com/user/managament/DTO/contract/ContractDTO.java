package com.user.managament.DTO.contract;

import java.time.LocalDate;
import java.util.UUID;

public record ContractDTO(
        UUID id,
        String paymentType,
        String contractStatus,
        LocalDate initDate,
        LocalDate endDate,
        Double price,
        LocalDate createDate
) {
}

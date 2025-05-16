package com.user.managament.DTO.contract;

import java.time.LocalDate;

public record ContractToEditDTO(String paymentType, String contractStatus, LocalDate initDate, LocalDate endDate, Double price) {
}

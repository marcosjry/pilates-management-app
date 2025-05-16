package com.user.managament.DTO.contract;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

public record ContractToCreateDTO(

        @NotBlank(message = "O tipo de pagamneto não pode estar em branco.")
        @NotNull(message = "O tipo de pagamneto não pode ser NULL.")
        String paymentType,

        @NotBlank(message = "O Status do Contrato não pode estar em branco.")
        @NotNull(message = "O Status do Contrato não pode ser NULL.")
        String contractStatus,

        @NotNull(message = "Data inicial não pode ser NULL.")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate initDate,

        @NotNull(message = "Preço não pode ser NULL.")
        Double price,

        @NotNull(message = "Customer não pode ser NULL.")
        UUID customerId
) { }

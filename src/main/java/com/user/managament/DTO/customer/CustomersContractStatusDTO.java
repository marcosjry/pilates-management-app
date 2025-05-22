package com.user.managament.DTO.customer;

import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.contract.PaymentType;

import java.util.UUID;

public record CustomersContractStatusDTO(
        UUID id,
        String name,
        ClassroomType classroomType,
        ContractStatus contractStatus,
        PaymentType paymentType
        ) {
}

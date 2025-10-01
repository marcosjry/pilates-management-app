package com.user.managament.customer.dto;

import com.user.managament.shared.model.classroom.ClassroomType;
import com.user.managament.shared.model.contract.ContractStatus;
import com.user.managament.shared.model.contract.PaymentType;

import java.util.UUID;

public record CustomersContractStatusDTO(
        UUID id,
        String name,
        ClassroomType classroomType,
        ContractStatus contractStatus,
        PaymentType paymentType
        ) {
}

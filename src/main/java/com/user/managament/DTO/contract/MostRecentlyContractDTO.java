package com.user.managament.DTO.contract;

import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.contract.PaymentType;

public record MostRecentlyContractDTO(
        ClassroomType classroomType,
        PaymentType paymentType,
        ContractStatus status
) {
}

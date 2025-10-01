package com.user.managament.contract.dto;

import com.user.managament.shared.model.classroom.ClassroomType;
import com.user.managament.shared.model.contract.ContractStatus;
import com.user.managament.shared.model.contract.PaymentType;

public record MostRecentlyContractDTO(
        ClassroomType classroomType,
        PaymentType paymentType,
        ContractStatus status
) {
}

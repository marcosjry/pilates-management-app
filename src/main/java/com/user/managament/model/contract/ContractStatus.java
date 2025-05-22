package com.user.managament.model.contract;

import com.user.managament.model.classroom.ClassroomType;

public enum ContractStatus {
    PENDING("PENDENTE"),
    CANCEL("CANCELADO"),
    EXPIRED("FINALIZADO"),
    ACTIVE("ATIVO");

    private final String contractStatus;

    ContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public static ContractStatus fromString(String value) {
        for (ContractStatus contractStatus : ContractStatus.values()) {
            if (contractStatus.name().equalsIgnoreCase(value)) {
                return contractStatus;
            }
        }
        return null;
    }

    public String getContractStatus() {
        return contractStatus;
    }
}

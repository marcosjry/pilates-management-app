package com.user.managament.model.contract;

public enum ContractStatus {
    PENDING("PENDENTE"),
    CANCELED("CANCELADO"),
    ENDED("FINALIZADO"),
    ACTIVE("ATIVO");

    private final String contractStatus;

    ContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public String getContractStatus() {
        return contractStatus;
    }
}

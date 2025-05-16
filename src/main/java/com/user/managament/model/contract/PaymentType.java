package com.user.managament.model.contract;

public enum PaymentType {
    GYMPASS("GYMPASS"),
    CASH("DINHEIRO"),
    CC("CARTAO_CREDITO"),
    CD("CARTAO_DEBITO");

    private final String paymentType;

    PaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentType() {
        return paymentType;
    }
}

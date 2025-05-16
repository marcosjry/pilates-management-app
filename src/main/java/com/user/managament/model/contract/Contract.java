package com.user.managament.model.contract;

import com.user.managament.model.customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "table_contract")
public class Contract {

    public Contract(PaymentType paymentType, ContractStatus contractStatus, LocalDate initDate, LocalDate endDate, Double price, Customer customer, LocalDate createDate) {
        this.paymentType = paymentType;
        this.contractStatus = contractStatus;
        this.initDate = initDate;
        this.endDate = endDate;
        this.price = price;
        this.customer = customer;
        this.createDate = createDate;
    }

    public Contract() {}

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContractStatus contractStatus;

    private LocalDate createDate;

    @Column(nullable = false)
    private LocalDate initDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Double price;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getInitDate() {
        return initDate;
    }

    public void setInitDate(LocalDate initDate) {
        this.initDate = initDate;
    }

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}

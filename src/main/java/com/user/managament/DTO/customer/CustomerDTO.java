package com.user.managament.DTO.customer;

import com.user.managament.model.customer.Customer;

import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name,
        String cpf,
        String email,
        String phone) {

    public static CustomerDTO fromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getCpf(),
                customer.getEmail(),
                customer.getPhone()

        );
    }
}

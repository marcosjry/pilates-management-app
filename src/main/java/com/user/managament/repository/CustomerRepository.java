package com.user.managament.repository;

import com.user.managament.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByCpf(String Cpf);
}

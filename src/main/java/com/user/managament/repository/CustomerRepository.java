package com.user.managament.repository;

import com.user.managament.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = """
    SELECT COUNT(c.id)
    FROM table_customer c
    """, nativeQuery = true)
    Long getDashboardTotalsCustomers();

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByCpf(String Cpf);
}

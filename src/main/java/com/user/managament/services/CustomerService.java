package com.user.managament.services;

import com.user.managament.DTO.customer.CustomerDTO;
import com.user.managament.DTO.customer.CustomerToCreateDTO;
import com.user.managament.DTO.customer.CustomerToEdit;
import com.user.managament.exception.CustomerDoesntExistsException;
import com.user.managament.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {

    void doesCreateCustomer(CustomerToCreateDTO customerToCreateDTO);
    void doesDeleteCustomer(UUID id) throws Exception;
    void doesEditCustomer(UUID customerId, CustomerToEdit customerToEdit);
    CustomerDTO doesGetCustomer(UUID id);

    Customer findCustomerById(UUID customerId) throws CustomerDoesntExistsException;

    Long getDashboardTotalsCustomers();

    Page<CustomerDTO> searchCustomers(String query, Pageable pageable);

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByPhone(String phone);
    boolean existsById(UUID id);



}

package com.user.managament.services;

import com.user.managament.DTO.customer.*;
import com.user.managament.exception.CustomerDoesntExistsException;
import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.contract.PaymentType;
import com.user.managament.model.customer.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {

    void doesCreateCustomer(CustomerToCreateDTO customerToCreateDTO);
    void doesDeleteCustomer(UUID id) throws Exception;
    void doesEditCustomer(UUID customerId, CustomerToEdit customerToEdit);
    CustomerDTO doesGetCustomer(UUID id);

    Customer findCustomerById(UUID customerId) throws CustomerDoesntExistsException;

    Long getDashboardTotalsCustomers();

    List<CustomersContractStatusDTO> searchCustomersAndLastContractsByFilter(ClassroomType roomType, ContractStatus status, PaymentType pType, String name);

    List<CustomersFrequencyClassDTO> searchCustomersByFilter(ClassroomType roomType, PaymentType pType, String name);

    boolean existsByEmail(String email);
    boolean existsByCpf(String cpf);
    boolean existsByPhone(String phone);
    boolean existsById(UUID id);

}

package com.user.managament.customer.services;

import com.user.managament.customer.dto.*;
import com.user.managament.customer.exception.CustomerDoesntExistsException;
import com.user.managament.customer.model.Customer;
import com.user.managament.shared.model.classroom.ClassroomType;
import com.user.managament.shared.model.contract.ContractStatus;
import com.user.managament.shared.model.contract.PaymentType;

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

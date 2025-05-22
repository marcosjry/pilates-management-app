package com.user.managament.services.impl;

import com.user.managament.DTO.customer.CustomerDTO;
import com.user.managament.DTO.customer.CustomerToCreateDTO;
import com.user.managament.DTO.customer.CustomerToEdit;
import com.user.managament.DTO.customer.CustomersContractStatusDTO;
import com.user.managament.exception.CustomerDoesntExistsException;
import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.contract.PaymentType;
import com.user.managament.model.customer.Customer;
import com.user.managament.repository.ContractRepository;
import com.user.managament.repository.CustomerRepository;
import com.user.managament.services.CustomerService;
import com.user.managament.util.SharedUtilClass;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ContractRepository contractRepository;

    public void verifyProperties(String input, Function<String, Boolean> conversor, String mensagem) {
        boolean existsByInput = conversor.apply(input);
        if (existsByInput)
            throw new InvalidParameterException("Cliente com "+mensagem+" já cadastrado.");
    }

    @Transactional
    public void createCustomer(CustomerToCreateDTO customerToCreateDTO) {
        verifyProperties(customerToCreateDTO.phone(), this::existsByPhone, "Celular");
        verifyProperties(customerToCreateDTO.email(), this::existsByEmail, "Email");
        verifyProperties(customerToCreateDTO.cpf(), this::existsByCpf, "CPF");

        ClassroomType type = ClassroomType.fromString(customerToCreateDTO.classroomType());

        Customer customerToCreate = new Customer(
                customerToCreateDTO.name(),
                customerToCreateDTO.cpf(),
                customerToCreateDTO.phone(),
                customerToCreateDTO.email(),
                type,
                LocalDate.now()
        );

        this.customerRepository.save(customerToCreate);
    }

    @Override
    public void doesCreateCustomer(CustomerToCreateDTO customerToCreateDTO) {
        try {
            this.createCustomer(customerToCreateDTO);
        } catch (InvalidParameterException e) {
            throw new InvalidParameterException(e.getMessage());
        }
    }

    @Override
    public void doesDeleteCustomer(UUID id) throws Exception {
        try {
            this.deleteCustomer(id);
        } catch (InvalidParameterException e) {
            throw new InvalidParameterException(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Error at delete of Customer.");
        }
    }

    @Override
    public CustomerDTO doesGetCustomer(UUID id) {
        try {
            return this.getCustomer(id);
        } catch (CustomerDoesntExistsException e) {
            throw new CustomerDoesntExistsException(e.getMessage());
        }
    }

    public CustomerDTO getCustomer(UUID id) throws CustomerDoesntExistsException {
            Customer customer = findCustomerById(id);
            return new CustomerDTO(
                    customer.getId(),
                    customer.getName(),
                    customer.getCpf(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getClassroomType().toString()
            );
    }

    @Override
    public List<CustomersContractStatusDTO> searchCustomersAndLastContractsByFilter(ClassroomType roomType, ContractStatus status, PaymentType pType, String name) {
        return customerRepository.findCustomerContractsDTOByFilters(roomType, status, pType, name);
    }

    @Transactional
    public void deleteCustomer(UUID id) {
        boolean existsById = this.existsById(id);
        if(!existsById)
            throw new InvalidParameterException("Customer ID doesnt Exist.");

        this.customerRepository.deleteById(id);
    }

    @Override
    public void doesEditCustomer(UUID idCustomer, CustomerToEdit customerToEdit) {
        try {
            editCustomer(idCustomer, customerToEdit);
        } catch (CustomerDoesntExistsException e) {
            throw new CustomerDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public Customer findCustomerById(UUID customerId) throws CustomerDoesntExistsException {
        return this.customerRepository.findById(customerId).orElseThrow(() -> new CustomerDoesntExistsException("Customer doesnt Exists."));
    }

    @Override
    public Long getDashboardTotalsCustomers() {
        return this.customerRepository.getDashboardTotalsCustomers();
    }

    private Customer changeFieldsToEdit(CustomerToEdit customerToEdit, Customer customer) {
        if(SharedUtilClass.setPropToEdit(customerToEdit.classroomType()))
            customer.setClassroomType(ClassroomType.valueOf(customerToEdit.classroomType()));

        if(SharedUtilClass.setPropToEdit(customerToEdit.name()))
            customer.setName(customerToEdit.name());

        if(SharedUtilClass.setPropToEdit(customerToEdit.cpf()))
            customer.setCpf(customerToEdit.cpf());

        if(SharedUtilClass.setPropToEdit(customerToEdit.email()))
            customer.setEmail(customerToEdit.email());

        if(SharedUtilClass.setPropToEdit(customerToEdit.phone()))
            customer.setPhone(customerToEdit.phone());

        return customer;
    }

    @Transactional
    public void editCustomer(UUID idCustomer, CustomerToEdit customerToEdit) throws CustomerDoesntExistsException {
        Customer customer = findById(idCustomer);
        customer = changeFieldsToEdit(customerToEdit, customer);
        this.customerRepository.save(customer);
    }

    public Customer findById(UUID customerId) throws CustomerDoesntExistsException {
        return this.customerRepository.findById(customerId).orElseThrow(() -> new CustomerDoesntExistsException("Customer Não existe."));
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByCpf(String cpf) {
        return this.customerRepository.existsByCpf(cpf);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return this.customerRepository.existsByPhone(phone);
    }

    @Override
    public boolean existsById(UUID id) {
        return this.customerRepository.existsById(id);
    }
}

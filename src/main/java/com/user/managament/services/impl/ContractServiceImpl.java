package com.user.managament.services.impl;

import com.user.managament.DTO.contract.ContractDTO;
import com.user.managament.DTO.contract.ContractToCreateDTO;
import com.user.managament.DTO.contract.ContractToEditDTO;
import com.user.managament.exception.ContractDoesntExistsException;
import com.user.managament.exception.CustomerDoesntExistsException;
import com.user.managament.model.contract.Contract;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.customer.Customer;
import com.user.managament.model.contract.PaymentType;
import com.user.managament.repository.ContractRepository;
import com.user.managament.services.ContractService;
import com.user.managament.services.CustomerService;
import com.user.managament.util.SharedUtilClass;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerService customerService;


    @Override
    public void doesCreateContract(ContractToCreateDTO contractToCreateDTO) {
        try {
            createContract(contractToCreateDTO);
        } catch (CustomerDoesntExistsException e) {
            throw new CustomerDoesntExistsException(e.getMessage());
        }
    }


    @Override
    public Contract findById(UUID id) throws ContractDoesntExistsException {
        return this.contractRepository.findById(id).orElseThrow(() -> new ContractDoesntExistsException("Contract doesnt Exist."));
    }

    @Transactional
    public void createContract(ContractToCreateDTO contractToCreateDTO) throws CustomerDoesntExistsException {
        LocalDate endDate = contractToCreateDTO.initDate().plusMonths(1);

        Customer customer = customerService.findCustomerById(contractToCreateDTO.customerId());

        Contract contract = new Contract(
                PaymentType.valueOf(contractToCreateDTO.paymentType()),
                ContractStatus.valueOf(contractToCreateDTO.contractStatus()),
                contractToCreateDTO.initDate(),
                endDate,
                contractToCreateDTO.price(),
                customer,
                LocalDate.now()
        );

        this.contractRepository.save(contract);
    }

    @Override
    public void doesEditContract(UUID id, ContractToEditDTO contractToEditDTO) {
        try {
            editContract(id, contractToEditDTO);
        } catch (ContractDoesntExistsException e) {
            throw new ContractDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public void deleteById(UUID id) {
        this.contractRepository.deleteById(id);
    }

    public Contract changeFieldsOnContract(ContractToEditDTO contractToEditDTO, Contract contract) {
        if(SharedUtilClass.setPropToEdit(contractToEditDTO.contractStatus()))
            contract.setContractStatus(ContractStatus.valueOf(contractToEditDTO.contractStatus()));

        if(SharedUtilClass.setPropToEdit(contractToEditDTO.paymentType()))
            contract.setPaymentType(PaymentType.valueOf(contractToEditDTO.paymentType()));

        if(SharedUtilClass.setPropToEdit(contractToEditDTO.initDate().toString())) {
            contract.setInitDate(contractToEditDTO.initDate());
            contract.setEndDate(contract.getInitDate().plusMonths(1));
        }

        if(SharedUtilClass.setPropToEdit(contractToEditDTO.price().toString()))
            contract.setPrice(contractToEditDTO.price());

        return contract;
    }

    @Transactional
    public void editContract(UUID contractId, ContractToEditDTO contractToEditDTO) throws ContractDoesntExistsException {
        Contract contract = this.findById(contractId);
        contract = changeFieldsOnContract(contractToEditDTO, contract);
        this.contractRepository.save(contract);
    }

    @Override
    public boolean existsById(UUID contractId) {
        return this.contractRepository.existsById(contractId);
    }


    public void deleteContract(UUID contractId) throws ContractDoesntExistsException {
        boolean existsById = existsById(contractId);
        if(!existsById)
            throw new ContractDoesntExistsException("Contract doesnt exists.");

        this.deleteById(contractId);
    }

    @Override
    public void doesDeleteContract(UUID contractId) {
        try {
            deleteContract(contractId);
        } catch (ContractDoesntExistsException e) {
            throw new ContractDoesntExistsException(e.getMessage());
        }
    }

    @Override
    public List<ContractDTO> getContractsFromCustomerId(UUID customerId) {
        List<Contract> contractsList = this.contractRepository.getContractsFromCustomerId(customerId);
        return SharedUtilClass.retornaListaFormatada(
                contractsList,
                contract -> new ContractDTO(
                        contract.getId(),
                        contract.getPaymentType().toString(),
                        contract.getContractStatus().toString(),
                        contract.getInitDate(),
                        contract.getEndDate(),
                        contract.getPrice(),
                        contract.getCreateDate()
                )
        );
    }

    @Override
    public List<ContractDTO> findLastContractByCustomerId(UUID customerId) {
        try {
            Contract contract = findLastContractForUser(customerId);
            return List.of(
                    new ContractDTO(
                    contract.getId(),
                    contract.getPaymentType().toString(),
                    contract.getContractStatus().toString(),
                    contract.getInitDate(),
                    contract.getEndDate(),
                    contract.getPrice(),
                    contract.getCreateDate()
            ));
        } catch (ContractDoesntExistsException e) {
            return List.of();
        }
    }

    public Contract findLastContractForUser(UUID customerId) throws ContractDoesntExistsException {
        return this.contractRepository.findTopByCustomerIdOrderByInitDateDesc(customerId).orElseThrow(
                () -> new ContractDoesntExistsException("Contract doesnt exists.")
        );
    }


}

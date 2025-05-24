package com.user.managament.services.impl;

import com.user.managament.DTO.contract.*;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

@Service
public class ContractServiceImpl implements ContractService {

    @Autowired
    private ContractRepository contractRepository;

    @Autowired
    private CustomerService customerService;

    private static final Logger log = LoggerFactory.getLogger(ContractService.class);

    // Boa Prática: Definir o fuso horário de negócio centralmente
    private static final ZoneId BUSINESS_ZONE_ID = ZoneId.of("America/Sao_Paulo");

    @Transactional
    public void expireDueContracts() {
        // Obter a data atual no fuso horário de negócio definido
        LocalDate today = LocalDate.now(BUSINESS_ZONE_ID);
        log.info("Iniciando verificação de contratos expirados para datas anteriores a: {} (Fuso: {})", today, BUSINESS_ZONE_ID);

        List<Contract> contractsToExpire = contractRepository.findByContractStatusAndEndDateLessThan(
                ContractStatus.ACTIVE, // Usando seu enum
                today
        );

        if (contractsToExpire.isEmpty()) {
            log.info("Nenhum contrato encontrado para expirar.");
            return;
        }

        log.info("Encontrados {} contratos para expirar.", contractsToExpire.size());

        for (Contract contract : contractsToExpire) {
            try {
                log.info("Expirando contrato ID: {}, Cliente: {}, Data Fim: {}",
                        contract.getId(), contract.getCustomer().getId(), contract.getEndDate()); // Ajustei o log

                contract.setContractStatus(ContractStatus.EXPIRED); // Usando seu enum
                contractRepository.save(contract);

            } catch (Exception e) {
                log.error("Erro ao expirar contrato ID: {}", contract.getId(), e);
            }
        }

        log.info("Verificação de contratos expirados concluída.");
    }

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

    @Override
    public List<ContractsExpiring> findExpiringContracts() {
        LocalDate start = LocalDate.now();
        LocalDate end = start.plusDays(7);
        return this.contractRepository.getContractsWithClientsExpiring(start, end);
    }

    @Override
    public MostRecentlyContractDTO findCustomerLastContractInfo(UUID customerId) {
        return this.contractRepository.findCustomerContractsDTOById(customerId);
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
    public List<ContractsAndCustomerDTO> getContracts(String query, String name) {
        ContractStatus status = ContractStatus.fromString(query);
        return this.contractRepository.getContractsWithClients(status, name);
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

    @Override
    public ActiveContractsWithCustomersDTO getTotalActiveContractsAndClients() {
        Long totalActiveContracts = this.contractRepository.getDashboardTotalsActiveContracts();
        Long totalCustomers = this.customerService.getDashboardTotalsCustomers();

        return new ActiveContractsWithCustomersDTO(totalCustomers, totalActiveContracts);
    }

    public Contract findLastContractForUser(UUID customerId) throws ContractDoesntExistsException {
        return this.contractRepository.findTopByCustomerIdOrderByInitDateDesc(customerId).orElseThrow(
                () -> new ContractDoesntExistsException("Contract doesnt exists.")
        );
    }


}

package com.user.managament.services;

import com.user.managament.DTO.contract.*;
import com.user.managament.exception.ContractDoesntExistsException;
import com.user.managament.model.contract.Contract;

import java.util.List;
import java.util.UUID;

public interface ContractService {

    void doesCreateContract(ContractToCreateDTO contractToCreateDTO);
    void doesEditContract(UUID id, ContractToEditDTO contractToEditDTO);
    void doesDeleteContract(UUID contractId);

    List<ContractsAndCustomerDTO> getContracts(String query, String name);

    List<ContractDTO> getContractsFromCustomerId(UUID customerId);
    List<ContractDTO> findLastContractByCustomerId(UUID customerId);

    ActiveContractsWithCustomersDTO getTotalActiveContractsAndClients();

    boolean existsById(UUID contractId);

    void deleteById(UUID id);
    Contract findById(UUID id) throws ContractDoesntExistsException;

    List<ContractsExpiring> findExpiringContracts();

    MostRecentlyContractDTO findCustomerLastContractInfo(UUID customerId);

    void expireDueContracts();
}

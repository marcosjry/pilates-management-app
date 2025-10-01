package com.user.managament.contract.services;

import com.user.managament.contract.dto.*;
import com.user.managament.contract.exception.ContractDoesntExistsException;
import com.user.managament.contract.model.Contract;

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

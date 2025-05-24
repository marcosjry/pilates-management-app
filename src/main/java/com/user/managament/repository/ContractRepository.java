package com.user.managament.repository;

import com.user.managament.DTO.classroom.ClassroomWithCountDTO;
import com.user.managament.DTO.contract.ActiveContractsWithCustomersDTO;
import com.user.managament.DTO.contract.ContractsAndCustomerDTO;
import com.user.managament.DTO.contract.ContractsExpiring;
import com.user.managament.DTO.contract.MostRecentlyContractDTO;
import com.user.managament.DTO.customer.CustomersContractStatusDTO;
import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.contract.Contract;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.contract.PaymentType;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContractRepository extends JpaRepository<Contract, UUID> {

    boolean existsById(UUID id);

    @Query(""" 
            SELECT c FROM Contract c WHERE c.customer.id = :customerId ORDER BY c.initDate desc
            """)
    List<Contract> getContractsFromCustomerId(@Param("customerId") UUID customerId);

    Optional<Contract> findTopByCustomerIdOrderByInitDateDesc(UUID customerId);

    @Query(value = """
    SELECT COUNT(tc.id)
    FROM table_contract tc
    JOIN table_customer cu ON cu.id = tc.customer_id
    """, nativeQuery = true)
    Long getDashboardTotalsActiveContracts();

    @Query("""
    SELECT new com.user.managament.DTO.contract.ContractsExpiring(
        c.id,
        c.name,
        tc.endDate,
        tc.contractStatus
    )
    FROM Contract tc
    JOIN tc.customer c
    WHERE tc.endDate BETWEEN :dateInit AND :end
    """)
    List<ContractsExpiring> getContractsWithClientsExpiring(@Param("dateInit") LocalDate dateInit, @Param("end") LocalDate end);

    @Query("""
    SELECT new com.user.managament.DTO.contract.ContractsAndCustomerDTO(
        c.id,
        c.name,
        tc.paymentType,
        tc.id,
        tc.initDate,
        tc.endDate,
        tc.price,
        tc.contractStatus
    )
    FROM Contract tc
    JOIN tc.customer c
    WHERE (:status IS NULL OR tc.contractStatus = :status)
    AND (:name IS NULL OR LOWER(CAST(c.name AS string)) LIKE LOWER(CONCAT('%', :name, '%')))
    """)
    List<ContractsAndCustomerDTO> getContractsWithClients(
            @Param("status") ContractStatus status,
            @Param("name") String name);

    List<Contract> findByContractStatusAndEndDateLessThan(ContractStatus status, LocalDate today);

    @Query("SELECT new com.user.managament.DTO.contract.MostRecentlyContractDTO(" +
            "c.classroomType, " +
            "last_contract.paymentType, last_contract.contractStatus) " +
            "FROM Customer c " +
            "LEFT JOIN Contract last_contract ON last_contract.customer.id = c.id " +
            "AND last_contract.initDate = (SELECT MAX(c_sub.initDate) FROM Contract c_sub WHERE c_sub.customer = c) " +
            "WHERE c.id = :customerId"
    )
    MostRecentlyContractDTO findCustomerContractsDTOById(
            @Param("customerId") UUID customerId
    );
}

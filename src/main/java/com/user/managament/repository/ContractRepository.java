package com.user.managament.repository;

import com.user.managament.DTO.classroom.ClassroomWithCountDTO;
import com.user.managament.DTO.contract.ActiveContractsWithCustomersDTO;
import com.user.managament.model.contract.Contract;
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

    @Query(""" 
            SELECT c FROM Contract c WHERE c.customer.id = :customerId ORDER BY c.initDate desc
            """)
    List<Contract> getActiveContractsFromCustomerId(@Param("customerId") UUID customerId);

    Optional<Contract> findTopByCustomerIdOrderByInitDateDesc(UUID customerId);

    @Query(value = """
    SELECT COUNT(tc.id)
    FROM table_contract tc
    JOIN table_customer cu ON cu.id = tc.customer_id
    """, nativeQuery = true)
    Long getDashboardTotalsActiveContracts();

}

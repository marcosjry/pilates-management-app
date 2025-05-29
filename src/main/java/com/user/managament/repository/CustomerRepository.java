package com.user.managament.repository;

import com.user.managament.DTO.customer.CustomersContractStatusDTO;
import com.user.managament.DTO.customer.CustomersFrequencyClassDTO;
import com.user.managament.model.classroom.ClassroomType;
import com.user.managament.model.contract.ContractStatus;
import com.user.managament.model.contract.PaymentType;
import com.user.managament.model.customer.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID>, JpaSpecificationExecutor<Customer> {

    Page<Customer> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query(value = """
    SELECT COUNT(c.id)
    FROM table_customer c
    """, nativeQuery = true)
    Long getDashboardTotalsCustomers();

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByCpf(String Cpf);

    @Query("SELECT new com.user.managament.DTO.customer.CustomersContractStatusDTO(" +
            "c.id, c.name, c.classroomType, " +
            "last_contract.contractStatus, last_contract.paymentType) " +
            "FROM Customer c " +
            "LEFT JOIN Contract last_contract ON last_contract.customer = c " +
            "AND last_contract.initDate = (SELECT MAX(c_sub.initDate) FROM Contract c_sub WHERE c_sub.customer = c) " +
            "WHERE (:roomType IS NULL OR c.classroomType = :roomType) " +
            "AND (:name IS NULL OR LOWER(CAST(c.name AS string)) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:status IS NULL OR last_contract.contractStatus = :status) " +
            "AND (:pType IS NULL OR last_contract.paymentType = :pType)"
    )
    List<CustomersContractStatusDTO> findCustomerContractsDTOByFilters(
            @Param("roomType") ClassroomType roomType,
            @Param("status") ContractStatus status,
            @Param("pType") PaymentType pType,
            @Param("name") String name
    );

    @Query("SELECT new com.user.managament.DTO.customer.CustomersFrequencyClassDTO(" +
            "c.id, c.name, c.classroomType)" +
            "FROM Customer c " +
            "LEFT JOIN Contract last_contract ON last_contract.customer = c " +
            "AND last_contract.initDate = (SELECT MAX(c_sub.initDate) FROM Contract c_sub WHERE c_sub.customer = c) " +
            "WHERE (:roomType IS NULL OR c.classroomType = :roomType) " +
            "AND (:name IS NULL OR LOWER(CAST(c.name AS string)) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:pType IS NULL OR last_contract.paymentType = :pType)" +
            "AND (last_contract.contractStatus = ACTIVE)"
    )
    List<CustomersFrequencyClassDTO> findAvailableCustomersDTO(
            @Param("roomType") ClassroomType roomType,
            @Param("pType") PaymentType pType,
            @Param("name") String name
    );

}

package com.user.managament.model.classroom;


import com.user.managament.model.customer.Customer;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(
        name = "classroom_frequency", // O nome real da sua tabela
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_aluno_turma_data",
                        columnNames = {"customer_id", "classroom_id", "classroom_date"}
                )
        }
)
public class ClassroomFrequency {

    public ClassroomFrequency(Classroom classroom, Customer customer, LocalDate classroomDate) {
        this.classroom = classroom;
        this.customer = customer;
        this.classroomDate = classroomDate;
    }

    public ClassroomFrequency() {

    }

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "classroom_id", nullable = false)
    private Classroom classroom;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    private LocalDate classroomDate;

    public LocalDate getClassroomDate() {
        return classroomDate;
    }

    public void setClassroomDate(LocalDate classroomDate) {
        this.classroomDate = classroomDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Classroom getClassroom() {
        return classroom;
    }

    public void setClassroom(Classroom classroom) {
        this.classroom = classroom;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}

package com.user.managament.services.impl;

import com.user.managament.DTO.classroom.FrequencyBatchDTO;
import com.user.managament.DTO.classroom.FrequencyClassToCreateDTO;
import com.user.managament.DTO.customer.FrequencyByDateAndHourDTO;
import com.user.managament.DTO.customer.CustomerDTO;
import com.user.managament.DTO.customer.CustomersFrequencyClassDTO;
import com.user.managament.controller.FrequencyClassController;
import com.user.managament.exception.ClassroomDoesntExistsException;
import com.user.managament.exception.ClassroomFrequencyDoesntExistsException;
import com.user.managament.exception.CustomerDoesntExistsException;
import com.user.managament.exception.FreqAlreadyExistsException;
import com.user.managament.model.classroom.Classroom;
import com.user.managament.model.classroom.ClassroomFrequency;
import com.user.managament.model.customer.Customer;
import com.user.managament.repository.FrequencyClassRepository;
import com.user.managament.services.FrequencyClassService;
import com.user.managament.services.ClassroomService;
import com.user.managament.services.CustomerService;
import com.user.managament.util.SharedUtilClass;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FrequencyClassServiceImpl implements FrequencyClassService {

    @Autowired
    private FrequencyClassRepository frequencyClassRepository;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private CustomerService customerService;

    @Override
    public void doesCreateFrequencyClassroom(FrequencyClassToCreateDTO frequencyClassToCreateDTO) {
        try {
            createFrequencyClassroom(frequencyClassToCreateDTO);
        } catch (ClassroomDoesntExistsException e) {
            throw new ClassroomDoesntExistsException(e.getMessage());
        } catch (CustomerDoesntExistsException e) {
            throw new CustomerDoesntExistsException(e.getMessage());
        }
    }

    @Transactional
    public void createFrequencyClassroom(FrequencyClassToCreateDTO frequencyClassToCreateDTO) throws ClassroomDoesntExistsException, CustomerDoesntExistsException {
        Classroom classroom = this.classroomService.findClassroomById(frequencyClassToCreateDTO.classroomId());

        Customer customer = this.customerService.findCustomerById(frequencyClassToCreateDTO.customerId());

        boolean freqExists = jaExisteFrequencia(frequencyClassToCreateDTO.customerId(),
                frequencyClassToCreateDTO.classroomId(), frequencyClassToCreateDTO.classDate(),
                classroom.getStartTime());
        if(freqExists)
            throw new FreqAlreadyExistsException("Frequência já registrada.");

        ClassroomFrequency classroomFrequency = new ClassroomFrequency(
                classroom,
                customer,
                frequencyClassToCreateDTO.classDate()
        );

        this.frequencyClassRepository.save(classroomFrequency);
    }

    public boolean jaExisteFrequencia(UUID customerId, UUID classroomId, LocalDate date, LocalTime hour) {
        return frequencyClassRepository.existsByCustomerIdAndClassroomIdAndClassroomDateAndClassroom_StartTime(
                customerId, classroomId, date, hour
        );
    }

    @Override
    public void doesDeleteFrequencyClassroom(UUID frequencyId) {
        try {
            deleteFrequencyClassroom(frequencyId);
        } catch (ClassroomFrequencyDoesntExistsException e) {
            throw new ClassroomFrequencyDoesntExistsException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void doesDeleteByCustomerIdAndClassroomId(UUID customerId, UUID classroomId) {
        this.frequencyClassRepository.deleteByCustomerIdAndClassroomId(customerId, classroomId);
    }

    @Override
    public void createFrequencyByBatch(FrequencyBatchDTO toCreate) {
        try {
            createByBatch(toCreate);
        } catch (Exception e) {
            throw new FreqAlreadyExistsException("Frequência já registrada");
        }


    }

    @Transactional
    public void createByBatch(FrequencyBatchDTO toCreate) {
        Classroom classroom = this.classroomService.findClassroomById(toCreate.classroomId());

        List<ClassroomFrequency> frequencyToSave = new ArrayList<>();

        for(UUID cutomerId: toCreate.customers()) {

            Customer customer = customerService.findCustomerById(cutomerId);

            ClassroomFrequency frequency = new ClassroomFrequency(
                    classroom,
                    customer,
                    toCreate.date()
            );

            frequencyToSave.add(frequency);

        }

        this.frequencyClassRepository.saveAll(frequencyToSave);
    }

    public void deleteFrequencyClassroom(UUID frequencyId) throws ClassroomFrequencyDoesntExistsException {
        boolean existsById = this.frequencyClassRepository.existsById(frequencyId);
        if(!existsById)
            throw new ClassroomFrequencyDoesntExistsException("Classroom frequency doesnt exists.");

        deleteFrequencyClassroomById(frequencyId);
    }

    public void deleteFrequencyClassroomById(UUID frequencyId){
        this.frequencyClassRepository.deleteById(frequencyId);
    }

    public List<Customer> getCustomersByFrequencyClass(LocalDate dateFilter,LocalTime timeFilter) {
        return this.frequencyClassRepository.findCustomersByDateAndTime(dateFilter, timeFilter);
    }

    public List<CustomersFrequencyClassDTO> doesGetCustomersByFrequencyClass(LocalDate date, LocalTime hour) {
        List<Customer> customerList = getCustomersByFrequencyClass(date, hour);
        return SharedUtilClass.retornaListaFormatada(customerList,
                customer -> new CustomersFrequencyClassDTO(
                        customer.getId(),
                        customer.getName(),
                        customer.getClassroomType()
                ));
    }
}

package com.user.managament.services;

import com.user.managament.DTO.user.CreateUserDTO;
import com.user.managament.exception.UserAlreadyExistsException;
import com.user.managament.exception.UserDoesntExistException;;
import com.user.managament.model.user.User;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface UserService {

    void createUser(CreateUserDTO createUserDTO) throws UserDoesntExistException, UserAlreadyExistsException, MethodArgumentNotValidException;

    User findUserByUserName(String userName) throws UserDoesntExistException;

    void doesCreateUser(CreateUserDTO createUserDTO) throws UserAlreadyExistsException, MethodArgumentNotValidException;

    boolean existsByUserName(String userName);
}

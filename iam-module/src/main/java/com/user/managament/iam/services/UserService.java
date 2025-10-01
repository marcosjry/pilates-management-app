package com.user.managament.iam.services;

import com.user.managament.iam.dto.CreateUserDTO;
import com.user.managament.iam.exception.UserAlreadyExistsException;
import com.user.managament.iam.exception.UserDoesntExistException;;
import com.user.managament.iam.model.User;
import org.springframework.web.bind.MethodArgumentNotValidException;

public interface UserService {

    void createUser(CreateUserDTO createUserDTO) throws UserDoesntExistException, UserAlreadyExistsException, MethodArgumentNotValidException;

    User findUserByUserName(String userName) throws UserDoesntExistException;

    void doesCreateUser(CreateUserDTO createUserDTO) throws UserAlreadyExistsException, MethodArgumentNotValidException;

    boolean existsByUserName(String userName);
}

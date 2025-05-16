package com.user.managament.services.impl;

import com.user.managament.DTO.user.CreateUserDTO;
import com.user.managament.config.SecurityConfig;
import com.user.managament.exception.UserAlreadyExistsException;
import com.user.managament.exception.UserDoesntExistException;
import com.user.managament.model.user.User;
import com.user.managament.repository.UserRepository;
import com.user.managament.services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void doesCreateUser(CreateUserDTO createUserDTO) throws MethodArgumentNotValidException {
        try {
            this.createUser(createUserDTO);
        }   catch (MethodArgumentNotValidException e) {
            throw new MethodArgumentNotValidException(e.getParameter(), e.getBindingResult());
        }   catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException (e.getMessage());
        }
    }

    @Override
    public void createUser(CreateUserDTO createUserDTO) throws UserAlreadyExistsException, MethodArgumentNotValidException {
        boolean userExists = existsByUserName(createUserDTO.userName());
        if(userExists)
            throw new UserAlreadyExistsException("UserName already exists.");

        User user = new User(
                createUserDTO.name(),
                createUserDTO.userName(),
                SecurityConfig.passwordEncoder().encode(createUserDTO.password())
        );
        this.userRepository.save(user);
    }

    @Override
    public User findUserByUserName(String userName) throws UserDoesntExistException {
        return this.userRepository.findByUserName(userName).orElseThrow(() -> new UserDoesntExistException("Usuário não Existe."));
    }

    @Override
    public boolean existsByUserName(String userName) {
        return this.userRepository.existsByUserName(userName);
    }


}

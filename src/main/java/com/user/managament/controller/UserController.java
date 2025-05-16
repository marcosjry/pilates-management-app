package com.user.managament.controller;

import com.user.managament.DTO.user.CreateUserDTO;
import com.user.managament.config.EndPointsAPI;
import com.user.managament.exception.UserAlreadyExistsException;
import com.user.managament.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = EndPointsAPI.USER)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, String>> createUser(@Valid @RequestBody CreateUserDTO createUserDTO) throws UserAlreadyExistsException, MethodArgumentNotValidException {
        userService.doesCreateUser(createUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("mensagem", "User created!"));
    }

}

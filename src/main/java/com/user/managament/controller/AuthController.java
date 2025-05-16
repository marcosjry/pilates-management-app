package com.user.managament.controller;

import com.user.managament.DTO.user.UserLoginReqDTO;
import com.user.managament.config.EndPointsAPI;
import com.user.managament.exception.UserAlreadyExistsException;
import com.user.managament.services.UserLoginService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path = EndPointsAPI.AUTH)
public class AuthController {

    @Autowired
    private UserLoginService userLoginService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authLogin(@Valid @RequestBody UserLoginReqDTO userLoginReqDTO) throws UserAlreadyExistsException {
        String message = userLoginService.doesAuthLogin(userLoginReqDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("token", message));
    }

    @GetMapping("/acess-error")
    public ResponseEntity<Map<String, String>> authError() {
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "acesso negado"));
    }
}

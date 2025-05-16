package com.user.managament.services.impl;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.user.managament.DTO.user.UserLoginReqDTO;
import com.user.managament.exception.UserDoesntExistException;
import com.user.managament.exception.UserPasswordMismatchException;
import com.user.managament.model.user.User;
import com.user.managament.services.TokenService;
import com.user.managament.services.UserLoginService;
import com.user.managament.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Override
    public String doesAuthLogin(UserLoginReqDTO userLoginReqDTO) {
        try {
            return authLogin(userLoginReqDTO);
        } catch (UserDoesntExistException e) {
            throw new UserDoesntExistException(e.getMessage());
        } catch (UserPasswordMismatchException e) {
            throw new UserPasswordMismatchException(e.getMessage());
        }
    }

    public String authLogin(UserLoginReqDTO userLoginReqDTO) throws UserDoesntExistException, UserPasswordMismatchException {
        User user = userService.findUserByUserName(userLoginReqDTO.userName());

        boolean validPassword = passwordEncoder.matches(userLoginReqDTO.password(), user.getPassword());
        if(!validPassword)
            throw new UserPasswordMismatchException("Usuário ou senha Inválido.");

        return tokenService.generateToken(user.getUserName());
    }

    @Override
    public Map<String, String> validaToken(String token) {
        return this.realizaValidacaoToken(token);
    }

    public Map<String, String> realizaValidacaoToken(String token) {
        try {
            String message = this.tokenService.validateToken(token);
            return Map.of("message", message);
        } catch (JWTVerificationException e) {
            return Map.of("message", e.getMessage());
        }
    }
}

package com.user.managament.services;

import com.user.managament.DTO.user.UserLoginReqDTO;
import com.user.managament.exception.UserDoesntExistException;
import com.user.managament.exception.UserPasswordMismatchException;

import java.util.Map;

public interface UserLoginService {

    String doesAuthLogin(UserLoginReqDTO usuarioLoginDTO);

    Map<String, String> validaToken(String token);
}

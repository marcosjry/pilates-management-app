package com.user.managament.iam.services;

import com.user.managament.iam.dto.UserLoginReqDTO;

import java.util.Map;

public interface UserLoginService {

    String doesAuthLogin(UserLoginReqDTO usuarioLoginDTO);

    Map<String, String> validaToken(String token);
}

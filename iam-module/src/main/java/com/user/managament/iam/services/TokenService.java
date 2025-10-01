package com.user.managament.iam.services;

public interface TokenService {

    String generateToken(String userName);

    String validateToken(String token);

    String extractRole(String token);
}

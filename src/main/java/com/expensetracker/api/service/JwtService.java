package com.expensetracker.api.service;

import com.expensetracker.api.model.Role;

public interface JwtService {

     String generateToken(String username , Role role);

     String extractUsername(String token);

     String extractRole(String token);
}

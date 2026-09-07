package com.expensetracker.api.service;

import com.expensetracker.api.DTO.UserResponse;
import java.util.*;

public interface UserService {

     List<UserResponse> getAllUsers();

     UserResponse getUserById(int id);

     void deleteUserById(int userId);
}


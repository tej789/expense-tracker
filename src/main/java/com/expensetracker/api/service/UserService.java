package com.expensetracker.api.service;

import com.expensetracker.api.DTO.UpdateProfileRequest;
import com.expensetracker.api.DTO.UserResponse;
import org.springframework.data.domain.Page;

import java.util.*;

public interface UserService {

     Page<UserResponse> getAllUser(int page, int size);

     UserResponse getUserById(int id);

     void deleteUserById(int userId);

     void updateProfile(UpdateProfileRequest request);

}


package com.expensetracker.api.service;

import com.expensetracker.api.DTO.LoginRequest;
import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.DTO.RegisterResponse;
import com.expensetracker.api.DTO.UserResponse;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import com.expensetracker.api.model.Role;
@Service
public class UserService {

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserResponse> u =new ArrayList<>();

        for(User user : users){

            UserResponse res =new UserResponse();

            res.setId(user.getId());
            res.setUsername(user.getUsername());
            res.setFirstname(user.getFirstname());
            res.setLastname(user.getLastname());
            res.setMail(user.getMail());
            res.setPhone(user.getPhone());
            res.setRole(user.getRole());

            u.add(res);
        }
return u;
    }


    public UserResponse getUserById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        UserResponse res = new UserResponse();

        res.setId(user.getId());
        res.setUsername(user.getUsername());
        res.setFirstname(user.getFirstname());
        res.setLastname(user.getLastname());
        res.setMail(user.getMail());
        res.setPhone(user.getPhone());
        res.setRole(user.getRole());

        return res;
    }

    public void deleteUserById(int id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        userRepository.delete(user);

    }
}
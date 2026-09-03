package com.expensetracker.api.service;

import com.expensetracker.api.DTO.LoginRequest;
import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.DTO.RegisterResponse;
import com.expensetracker.api.DTO.UserResponse;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

import com.expensetracker.api.model.Role;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;


    public UserService(UserRepository userRepository,BudgetRepository budgetRepository,TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.budgetRepository =budgetRepository;
        this.transactionRepository =transactionRepository;
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


    @Transactional
    public void deleteUserById(int userId) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new NoSuchElementException("Admin not found"));

        if (currentUser.getId() == userId)
        {throw new IllegalArgumentException(
                    "Admin cannot delete their own account");
        }

        User user = userRepository.findById(userId).orElseThrow(() ->
                        new NoSuchElementException("User not found"));

        transactionRepository.deleteByUserId(userId);

        budgetRepository.deleteByUserId(userId);

        userRepository.delete(user);
    }
}


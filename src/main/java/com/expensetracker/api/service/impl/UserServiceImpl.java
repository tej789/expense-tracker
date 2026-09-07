package com.expensetracker.api.service.impl;

import com.expensetracker.api.DTO.UserResponse;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.BudgetRepository;
import com.expensetracker.api.repository.TransactionRepository;
import com.expensetracker.api.repository.UserRepository;
import com.expensetracker.api.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;


    public UserServiceImpl(UserRepository userRepository,BudgetRepository budgetRepository,TransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.budgetRepository =budgetRepository;
        this.transactionRepository =transactionRepository;
    }

    @Override
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

    @Override
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
    @Override
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

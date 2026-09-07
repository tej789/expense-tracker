package com.expensetracker.api.service.impl;


import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import com.expensetracker.api.service.CurrentUserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CurrentUserServiceImpl implements CurrentUserService {


    private final UserRepository userRepository;

    public CurrentUserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getCurrentUser(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));
    }
}

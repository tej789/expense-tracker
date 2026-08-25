package com.expensetracker.api.service;

import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import com.expensetracker.api.model.Role;
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
}

public String registerUser(RegisterRequest request){

    if(userRepository.existsByUsername(request.getUsername())){
        throw new IllegalArgumentException("User Already Exist");
    }

    User user =new User();

    user.setUsername(request.getUsername());
    user.setPassword(
            passwordEncoder.encode(request.getPassword())
    );
    user.setRole(Role.USER);

    userRepository.save(user);
    return "User Added";
}

  public User login(RegisterRequest request){

      User user = userRepository.findByUsername(request.getUsername())
              .orElseThrow(()-> new NoSuchElementException("User not found"));

      if(!passwordEncoder.matches(request.getPassword(),user.getPassword())){
          throw new IllegalArgumentException("Invalid password");
      }
      return user;
  }
}

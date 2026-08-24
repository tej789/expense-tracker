package com.expensetracker.api.service;

import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

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
        throw new RuntimeException("User Already Exist");
    }

    User user =new User();

    user.setUsername(request.getUsername());
    user.setPassword(
            passwordEncoder.encode(request.getPassword())
    );

    userRepository.save(user);
    return "User Added";
}

  public boolean login(RegisterRequest request){

      Optional<User> user = userRepository.findByUsername(request.getUsername())
              ;

      if(user.isPresent()){

          if(passwordEncoder.matches(request.getPassword(),user.get().getPassword())){
              return true;
          }
      }
return false;
  }
}

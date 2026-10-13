package com.expensetracker.api.config;


import com.expensetracker.api.model.Role;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class AdminInitializer {

    @Bean
public CommandLineRunner createAdmin(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        @Value("${app.admin.username}") String username,
        @Value("${app.admin.firstname}") String firstname,
        @Value("${app.admin.lastname}") String lastname,
        @Value("${app.admin.mail}") String mail,
        @Value("${app.admin.phone}") String phone,
        @Value("${app.admin.password}") String password)
{

    return args ->{
        boolean adminExists = userRepository.existsByRole(Role.ADMIN);

    if(!adminExists){
        User admin = User.builder()
                .username(username)
        .firstname(firstname)
                .lastname(lastname)
                .mail(mail)
                .phone(phone)
                .password(passwordEncoder.encode(password))
                .role(Role.ADMIN)
                .active(true)
                .emailVerified(true)
                .build();

        userRepository.save(admin);

        System.out.println("Initial admin created successfully.");
    }

    };
    }
}

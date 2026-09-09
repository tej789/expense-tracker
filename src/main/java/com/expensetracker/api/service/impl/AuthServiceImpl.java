package com.expensetracker.api.service.impl;

import com.expensetracker.api.DTO.LoginRequest;
import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.DTO.RegisterResponse;
import com.expensetracker.api.DTO.VerifyEmailRequest;
import com.expensetracker.api.model.Role;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import com.expensetracker.api.service.AuthService;
import com.expensetracker.api.service.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }


    @Override
  public void verifyEmail(VerifyEmailRequest request){

            User user = userRepository.findByMail(request.getMail())
                    .orElseThrow(()-> new NoSuchElementException("User Not Found"));

            if(user.isEmailVerified()){
                throw new IllegalArgumentException("User Already Verified");
            }

        if (user.getVerificationCodeExpiry() == null ||
                LocalDateTime.now().isAfter(user.getVerificationCodeExpiry())) {

            throw new IllegalArgumentException(
                    "Verification code has expired");
        }
        if (!request.getVerificationCode().equals(user.getVerificationCode())) {

            throw new IllegalArgumentException(
                    "Invalid verification code");
        }



        user.setEmailVerified(true);
        user.setVerificationCode(null);
        user.setVerificationCodeExpiry(null);

        userRepository.save(user);
    }


    @Override
    public RegisterResponse registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByMail(request.getMail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        if (userRepository.findByPhone(request.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Phone number already exists");
        }

        // generate OTP
        Random OTP = new Random();
        String verificationCode = String.format("%06d", OTP.nextInt(1000000));


        User user = new User();

        user.setUsername(request.getUsername());
        user.setFirstname(request.getFirstname());
        user.setLastname(request.getLastname());
        user.setMail(request.getMail());
        user.setPhone(request.getPhone());
        user.setPassword(
                passwordEncoder.encode(request.getPassword())
        );
        user.setRole(Role.USER);

        // set OTP
        user.setEmailVerified(false);
        user.setVerificationCode(verificationCode);
        user.setVerificationCodeExpiry(
                LocalDateTime.now().plusMinutes(10)
        );

        userRepository.save(user);

        // send OTP throught mail
        emailService.sendEmail(user.getMail(),
                "Expense Tracker Email Verification",
                "Your Verification Code is: "+verificationCode);


        return new RegisterResponse(
                "Registration successful. Please verify your email.",
                user.getUsername()
        );
    }

    @Override
    public User login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!user.isEmailVerified()) {
            throw new IllegalArgumentException(
                    "Please verify your email first"
            );
        }

        if(!user.isActive()){
            throw new IllegalArgumentException("User Account Is Inactive");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }
        return user;
    }

}

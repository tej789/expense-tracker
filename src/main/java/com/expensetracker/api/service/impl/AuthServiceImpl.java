package com.expensetracker.api.service.impl;

import com.expensetracker.api.DTO.*;
import com.expensetracker.api.model.Role;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import com.expensetracker.api.security.LoginRateLimitor;
import com.expensetracker.api.service.AuthService;
import com.expensetracker.api.service.OtpNotificationSubject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OtpNotificationSubject otpNotificationSubject;
    private final LoginRateLimitor loginRateLimitor;

    public AuthServiceImpl(LoginRateLimitor loginRateLimitor,UserRepository userRepository, PasswordEncoder passwordEncoder,OtpNotificationSubject otpNotificationSubject) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpNotificationSubject = otpNotificationSubject;
        this.loginRateLimitor = loginRateLimitor;
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



        user.setActive(true);
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

        User user = User.builder()
                .username(request.getUsername())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .mail(request.getMail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .active(false)
                .emailVerified(false)
                .verificationCode(verificationCode)
                .verificationCodeExpiry(LocalDateTime.now().plusMinutes(10))
                .build();


        userRepository.save(user);

        // send OTP through mail
        otpNotificationSubject.notifyObservers(
     user,verificationCode
        );



        return new RegisterResponse(
                "Registration successful. Please verify your email.",
                user.getUsername()
        );
    }

//    @Override
//    public User login(LoginRequest request) {
//
//        User user = userRepository.findByUsername(request.getUsername())
//                .orElseThrow(() -> new NoSuchElementException("User not found"));
//
//        if (!user.isEmailVerified()) {
//            throw new IllegalArgumentException(
//                    "Please verify your email first"
//            );
//        }
//
//        if(!user.isActive()){
//            throw new IllegalArgumentException("User Account Is Inactive");
//        }
//
//        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
//            throw new IllegalArgumentException("Invalid password");
//        }
//        return user;
//    }

@Override
public User login(LoginRequest request) {

        String username = request.getUsername();

        if(loginRateLimitor.isBlocked(username)){
            throw new IllegalArgumentException(
                    "Too many Login Attempts.Try After 15 Minutes."
            );
        }

User user = userRepository.findByUsername(username)
        .orElseThrow(() ->{
            loginRateLimitor.loginFailed(username);
            return new IllegalArgumentException(
                    "Invalid username or password"
            );
        });

    if (!user.isEmailVerified()) {
        throw new IllegalArgumentException(
                "Please verify your email first"
        );
    }

    if(!user.isActive()){
        throw new IllegalArgumentException("User Account Is Inactive");
    }

    if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

        loginRateLimitor.loginFailed(username);
        throw new IllegalArgumentException("Invalid username or password");
    }

    loginRateLimitor.loginSucceeded(username);

    return user;
}



    @Override
    public void forgotPassword(ForgotPasswordRequest request) {

        User user = userRepository.findByMail(request.getMail())
                .orElseThrow(() -> new NoSuchElementException("User Not Found"));

        SecureRandom OTP = new SecureRandom();

        String resetPasswordCode =
                String.format("%06d", OTP.nextInt(1000000));

        user.setResetPasswordCode(resetPasswordCode);

        user.setResetPasswordCodeExpiry(
                LocalDateTime.now().plusMinutes(10)
        );

        userRepository.save(user);

        otpNotificationSubject.notifyObservers(
                user,
                resetPasswordCode
        );
    }

    @Override
public void resetPassword(ResetPasswordRequest request){
        User user = userRepository.findByMail(request.getMail())
                .orElseThrow(()->
                        new NoSuchElementException("User Not Found"));

        if (user.getResetPasswordCode() == null ||
                user.getResetPasswordCodeExpiry() == null ||
                LocalDateTime.now().isAfter(
                        user.getResetPasswordCodeExpiry())) {

            throw new IllegalArgumentException(
                    "Reset password OTP has expired or is invalid");
        }

        if (!user.getResetPasswordCode()
                .equals(request.getResetPasswordCode())) {

            throw new IllegalArgumentException("Invalid OTP");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        user.setResetPasswordCode(null);
        user.setResetPasswordCodeExpiry(null);
        userRepository.save(user);

}

}

package com.expensetracker.api.service;

import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import com.expensetracker.api.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private OtpNotificationSubject otpNotificationSubject;

    @InjectMocks
    private AuthServiceImpl authService;

    @Test
    void shouldNotRegisterExistingUsername() {

        RegisterRequest request = new RegisterRequest();
        request.setUsername("USER3");
        request.setFirstname("Tej");
        request.setLastname("Patel");
        request.setMail("tej@example.com");
        request.setPhone("9583385944");
        request.setPassword("Password123");

        when(userRepository.findByUsername("USER3"))
                .thenReturn(Optional.of(new User()));

        assertThrows(
                IllegalArgumentException.class,
                () -> authService.registerUser(request)
        );


    }
}

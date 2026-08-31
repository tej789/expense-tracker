package com.expensetracker.api.service;

import com.expensetracker.api.DTO.RegisterRequest;
import com.expensetracker.api.model.Role;
import com.expensetracker.api.model.User;
import com.expensetracker.api.repository.UserRepository;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void ExistUserShouldNotRegister(){
        RegisterRequest user = new RegisterRequest();
        user.setUsername("USER3");
        user.setFirstname("x");
        user.setLastname("y");
        user.setMail("tejgoti2005@gmail.com");
        user.setPhone("9583385944");

        Mockito.when(userRepository.existsByUsername("USER3")).thenReturn(true);

        assertThrows(
                IllegalArgumentException.class,
                ()->authService.registerUser(user)
                );
    }

}

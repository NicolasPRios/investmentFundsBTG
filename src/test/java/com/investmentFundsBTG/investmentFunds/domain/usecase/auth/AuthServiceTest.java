package com.investmentFundsBTG.investmentFunds.domain.usecase.auth;

import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import com.investmentFundsBTG.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthService authService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        mockUser.setEmail("test@example.com");
        mockUser.setPassword("encodedPassword123");
    }

    @Test
    void login_Success() {

        when(userRepository.findByEmail("test@example.com")).thenReturn(mockUser);
        when(passwordEncoder.matches("rawPassword", "encodedPassword123")).thenReturn(true);
        when(jwtService.generateToken(mockUser)).thenReturn("mocked-jwt-token");

        String token = authService.login("test@example.com", "rawPassword");

        assertNotNull(token);
        assertEquals("mocked-jwt-token", token);
        verify(userRepository).findByEmail("test@example.com");
    }

    @Test
    void login_InvalidPassword_ThrowsException() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(mockUser);
        when(passwordEncoder.matches("wrongPassword", "encodedPassword123")).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            authService.login("test@example.com", "wrongPassword");
        });

        assertEquals("Credenciales inválidas", exception.getMessage());
        verify(jwtService, never()).generateToken(any());
    }
}

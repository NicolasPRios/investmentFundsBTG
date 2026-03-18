package com.investmentFundsBTG.investmentFunds.domain.usecase;

import com.investmentFundsBTG.investmentFunds.domain.model.common.BusinessException;
import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import com.investmentFundsBTG.investmentFunds.domain.model.user.gateways.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserUseCaseTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserUseCase userUseCase;

    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = User.builder()
                .email("nicolas@test.com")
                .password("password123")
                .availableBalance(600000L)
                .build();
    }

    @Test
    void saveUser_Success() {

        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(passwordEncoder.encode(anyString())).thenReturn("encoded_password");
        when(userRepository.saveUser(any(User.class))).thenReturn(newUser);

        User savedUser = userUseCase.saveUser(newUser);

        assertNotNull(savedUser);
        verify(passwordEncoder).encode("password123");
        verify(userRepository).saveUser(newUser);
    }

    @Test
    void saveUser_ThrowsException_WhenEmailAlreadyExists() {

        when(userRepository.findByEmail("nicolas@test.com")).thenReturn(new User());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userUseCase.saveUser(newUser);
        });

        assertEquals("Ya existe un usuario registrado con este email", exception.getMessage());
        assertEquals("EMAIL_REGISTRADO", exception.getCode());
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void saveUser_ThrowsException_WhenBalanceIsInsufficient() {

        User poorUser = newUser.toBuilder().availableBalance(100000L).build();
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            userUseCase.saveUser(poorUser);
        });

        assertTrue(exception.getMessage().contains("COP $500.000"));
        assertEquals("SALDO_MENOR", exception.getCode());
        verify(userRepository, never()).saveUser(any());
    }

    @Test
    void updateUser_Success() {

        when(userRepository.saveUser(any(User.class))).thenReturn(newUser);

        User updated = userUseCase.updateUser(newUser);

        assertNotNull(updated);
        verify(userRepository).saveUser(newUser);
    }
}

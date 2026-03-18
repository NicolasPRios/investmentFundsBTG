package com.investmentFundsBTG.investmentFunds.domain.usecase;

import com.investmentFundsBTG.investmentFunds.domain.model.common.BusinessException;
import com.investmentFundsBTG.investmentFunds.domain.model.fund.Fund;
import com.investmentFundsBTG.investmentFunds.domain.model.fund.gateways.FundRepository;
import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.gateways.InvestmentFundRepository;
import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import com.investmentFundsBTG.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFundsBTG.investmentFunds.domain.usecase.email.EmailService;
import com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints.DTO.InvestmentFundDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InvestmentFundUseCaseTest {
    @Mock private InvestmentFundRepository investmentFundRepository;
    @Mock private FundRepository fundRepository;
    @Mock private UserRepository userRepository;
    @Mock private EmailService emailService;

    @InjectMocks private InvestmentFundUseCase useCase;

    private User mockUser;
    private Fund mockFund;
    private InvestmentFundDTO mockDto;

    @BeforeEach
    void setUp() {
        mockUser = User.builder()
                .id("user123").email("test@mail.com").availableBalance(1000000L).build();

        mockFund = Fund.builder()
                .id("fund123").name("Fondo Deuda").minimumAmount(50000L).build();

        mockDto = new InvestmentFundDTO();
        mockDto.setIdFund("fund123");
        mockDto.setIdUser("user123");
        mockDto.setOpeningValue(100000L);
        mockDto.setMessagePreference(1);
        mockDto.setState("Aperturado");
    }

    @Test
    void save_Success_ShouldDeductBalanceAndSendEmail() {

        when(fundRepository.getById("fund123")).thenReturn(mockFund);
        when(userRepository.getById("user123")).thenReturn(mockUser);
        when(investmentFundRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        InvestmentFund result = useCase.save(mockDto);

        assertNotNull(result);
        assertEquals(900000L, mockUser.getAvailableBalance());
        verify(userRepository).saveUser(mockUser);
        verify(emailService).enviarCorreo(anyString(), anyString(), anyString());
        verify(investmentFundRepository).save(any(InvestmentFund.class));
    }

    @Test
    void save_ThrowsException_WhenBalanceIsLowerThanOpeningValue() {

        mockUser.setAvailableBalance(10000L);
        when(fundRepository.getById(any())).thenReturn(mockFund);
        when(userRepository.getById(any())).thenReturn(mockUser);

        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.save(mockDto));
        assertEquals("SALDO_INSUFICIENTE", ex.getCode());
        verify(investmentFundRepository, never()).save(any());
    }

    @Test
    void cancelSubscription_Success_ShouldReturnBalanceToUser() {

        InvestmentFund subscription = InvestmentFund.builder()
                .id("sub123")
                .state("Aperturado")
                .openingValue(100000L)
                .user(mockUser)
                .build();

        when(investmentFundRepository.byId("sub123")).thenReturn(subscription);

        InvestmentFund result = useCase.cancelSubscription("sub123");

        assertEquals("Cancelado", result.getState());
        assertEquals(1100000L, mockUser.getAvailableBalance()); // 1M + 100k
        verify(userRepository).saveUser(mockUser);
        verify(investmentFundRepository).save(subscription);
    }

    @Test
    void cancelSubscription_ThrowsException_WhenAlreadyCancelled() {

        InvestmentFund subscription = InvestmentFund.builder()
                .id("sub123").state("Cancelado").user(mockUser).build();

        when(investmentFundRepository.byId("sub123")).thenReturn(subscription);

        BusinessException ex = assertThrows(BusinessException.class, () -> useCase.cancelSubscription("sub123"));
        assertEquals("ESTADO_CANCELADO", ex.getCode());
    }

    @Test
    void findByIduser_ShouldCallRepository() {

        when(investmentFundRepository.findByIdUser("user123")).thenReturn(List.of());

        useCase.findByIduser("user123");

        verify(investmentFundRepository).findByIdUser("user123");
    }

    @Test
    void save_Success_ShouldPrintSMS_WhenPreferenceIs2() {

        mockDto.setMessagePreference(2);
        when(fundRepository.getById(any())).thenReturn(mockFund);
        when(userRepository.getById(any())).thenReturn(mockUser);
        when(investmentFundRepository.save(any())).thenReturn(new InvestmentFund());

        useCase.save(mockDto);

        verify(emailService, never()).enviarCorreo(any(), any(), any());
    }
}

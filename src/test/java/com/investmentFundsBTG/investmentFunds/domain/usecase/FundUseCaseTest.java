package com.investmentFundsBTG.investmentFunds.domain.usecase;

import com.investmentFundsBTG.investmentFunds.domain.model.fund.Fund;
import com.investmentFundsBTG.investmentFunds.domain.model.fund.gateways.FundRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FundUseCaseTest {
    @Mock
    private FundRepository fundRepository;

    @InjectMocks
    private FundUseCase fundUseCase;

    private Fund sampleFund;

    @BeforeEach
    void setUp() {
        sampleFund = Fund.builder()
                .id("65f123abc")
                .name("Fondo Balanceado")
                .minimumAmount(75000L)
                .category("FIC")
                .build();
    }

    @Test
    void saveFund_ShouldReturnSavedFund_WhenDataIsValid() {

        when(fundRepository.saveFund(any(Fund.class))).thenReturn(sampleFund);

        Fund result = fundUseCase.saveFund(sampleFund);

        assertNotNull(result);
        assertEquals("Fondo Balanceado", result.getName());
        assertEquals(75000L, result.getMinimumAmount());
        assertEquals("FIC", result.getCategory());

        verify(fundRepository, times(1)).saveFund(sampleFund);
    }

    @Test
    void saveFund_ShouldFail_WhenRepositoryThrowsException() {
        when(fundRepository.saveFund(any(Fund.class)))
                .thenThrow(new RuntimeException("Error persistencia"));

        assertThrows(RuntimeException.class, () -> {
            fundUseCase.saveFund(sampleFund);
        });
    }
}

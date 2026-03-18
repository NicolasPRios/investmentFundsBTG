package com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFundsBTG.investmentFunds.domain.usecase.InvestmentFundUseCase;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtAuthenticationFilter;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtService;
import com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints.DTO.InvestmentFundDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = InvestmentFundController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
@AutoConfigureMockMvc(addFilters = false)
public class InvestmentFundControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private InvestmentFundUseCase investmentFundUseCase;

    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Debe obtener todos los fondos por ID de usuario")
    void allInvestmentFundsByIdUserTest() throws Exception {

        String userId = "user123";
        InvestmentFund fund = InvestmentFund.builder().id("sub1").state("Aperturado").build();
        when(investmentFundUseCase.findByIduser(userId)).thenReturn(List.of(fund));

        mockMvc.perform(get("/")
                        .param("id", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("sub1"))
                .andExpect(jsonPath("$[0].state").value("Aperturado"));
    }

    @Test
    @DisplayName("Debe crear una suscripción exitosamente")
    void createInvestmentFundTest() throws Exception {

        InvestmentFundDTO dto = new InvestmentFundDTO();
        dto.setId("sub123");
        dto.setIdFund("fund123");
        dto.setIdUser("user123");
        dto.setOpeningValue(100000L);
        dto.setMessagePreference(1);
        dto.setState("Aperturado");

        InvestmentFund savedFund = InvestmentFund.builder()
                .id("sub123")
                .state("Aperturado")
                .build();

        when(investmentFundUseCase.save(any(InvestmentFundDTO.class))).thenReturn(savedFund);


        mockMvc.perform(post("/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("sub123"));
    }

    @Test
    @DisplayName("Debe cancelar una suscripción exitosamente")
    void cancelSubscriptionTest() throws Exception {

        String subId = "sub123";
        InvestmentFund cancelledFund = InvestmentFund.builder().id(subId).state("Cancelado").build();
        when(investmentFundUseCase.cancelSubscription(subId)).thenReturn(cancelledFund);

        mockMvc.perform(put("/")
                        .param("id", subId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("Cancelado"));
    }
}

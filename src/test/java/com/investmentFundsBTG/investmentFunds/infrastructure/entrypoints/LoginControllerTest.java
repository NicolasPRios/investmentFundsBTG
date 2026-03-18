package com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import com.investmentFundsBTG.investmentFunds.domain.usecase.auth.AuthService;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtService;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtAuthenticationFilter;
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

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = LoginController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
@AutoConfigureMockMvc(addFilters = false)
public class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LoginController loginController;

    @MockitoBean
    private AuthService authService;

    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Debe verificar que el controlador de login no sea nulo")
    void contextLoads() {
        assertNotNull(loginController);
    }

    @Test
    @DisplayName("Debe retornar un token cuando las credenciales son válidas")
    void loginSuccessTest() throws Exception {

        User loginRequest = User.builder()
                .email("nicolas@test.com")
                .password("password123")
                .build();

        String mockedToken = "mocked-jwt-token";

        when(authService.login("nicolas@test.com", "password123"))
                .thenReturn(mockedToken);

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(mockedToken));
    }
}

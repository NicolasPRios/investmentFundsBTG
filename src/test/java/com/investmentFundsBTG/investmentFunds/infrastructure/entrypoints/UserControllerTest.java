package com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import com.investmentFundsBTG.investmentFunds.domain.usecase.UserUseCase;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtAuthenticationFilter;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class, excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class)
})
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockitoBean
    private UserUseCase userUseCase;

    @MockitoBean
    private JwtService jwtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("Debe verificar que el controlador de usuarios no sea nulo")
    void contextLoads() {
        assertNotNull(userController);
    }

    @Test
    @DisplayName("Debe crear un usuario exitosamente y retornar 201 Created")
    void createdUserTest() throws Exception {
        User userRequest = User.builder()
                .id("user123")
                .name("Nicolas") // Faltaba este
                .email("nicolas@test.com")
                .password("password123")
                .cellphoneNumber("3001234567")
                .availableBalance(600000L)
                .build();


        when(userUseCase.saveUser(any(User.class))).thenReturn(userRequest);

        mockMvc.perform(post("/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Nicolas"))
                .andExpect(jsonPath("$.cellphoneNumber").value("3001234567"))
                .andExpect(jsonPath("$.email").value("nicolas@test.com"));
    }
}

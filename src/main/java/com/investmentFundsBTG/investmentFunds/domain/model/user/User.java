package com.investmentFundsBTG.investmentFunds.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    private String name;
    private Long availableBalance;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String email;
    private String cellphoneNumber;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String password;
}

package com.investmentFundsBTG.investmentFunds.domain.model.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    private String id;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String name;
    @NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private Long availableBalance;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String email;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String cellphoneNumber;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String password;
}

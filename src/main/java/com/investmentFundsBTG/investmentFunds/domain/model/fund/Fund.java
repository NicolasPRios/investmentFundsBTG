package com.investmentFundsBTG.investmentFunds.domain.model.fund;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class Fund {
    private String id;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String name;
    @NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private Long minimumAmount;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String category;
}

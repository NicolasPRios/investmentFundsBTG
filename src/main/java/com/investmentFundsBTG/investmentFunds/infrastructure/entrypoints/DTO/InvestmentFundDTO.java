package com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints.DTO;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentFundDTO {
    private String id;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String idFund;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String idUser;
    @NotBlank(message = "El campo no puede estar vacio o nulo")
    private String state;
    @NotNull(message = "El campo no puede estar vacío o nulo")
    @Min(value = 1, message = "El valor debe ser 1 o 2")
    @Max(value = 2, message = "El valor debe ser 1 o 2")
    private Integer messagePreference;
    @NotNull(message = "El saldo es obligatorio")
    @PositiveOrZero(message = "El saldo no puede ser negativo")
    private Long openingValue;
}

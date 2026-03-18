package com.investmentFundsBTG.investmentFunds.infrastructure.entity;


import com.investmentFundsBTG.investmentFunds.domain.model.fund.Fund;
import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvestmentFundEntity {
    @Id
    private Integer id;
    private Fund fund;
    private User user;
    private String state;
    private Integer messagePreference;
    private Long openingValue;
}

package com.investmentFundsBTG.investmentFunds.infrastructure.mapper;

import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFundsBTG.investmentFunds.infrastructure.entity.InvestmentFundEntity;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface InvestmentFundMapper {
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "fund", target = "fund"),
            @Mapping(source = "user", target = "user"),
            @Mapping(source = "state", target = "state"),
            @Mapping(source = "messagePreference", target = "messagePreference"),
            @Mapping(source = "openingValue", target = "openingValue")
    })
    InvestmentFund toInvestmentFund(InvestmentFundEntity investmentFund);

    Iterable<InvestmentFund> toInvestmentFunds(Iterable<InvestmentFundEntity> investmentFundEntities);

    @InheritConfiguration
    InvestmentFundEntity toInvestmentFundEntity(InvestmentFund investmentFund);
}

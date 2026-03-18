package com.investmentFundsBTG.investmentFunds.domain.model.fund.gateways;

import com.investmentFundsBTG.investmentFunds.domain.model.fund.Fund;

public interface FundRepository {
    Fund getById(Integer id);
    Fund saveFund(Fund fund);
}

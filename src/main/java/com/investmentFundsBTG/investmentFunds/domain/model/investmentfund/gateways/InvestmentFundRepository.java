package com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.gateways;

import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.InvestmentFund;

import java.util.List;

public interface InvestmentFundRepository {
    InvestmentFund byId(String id);
    InvestmentFund save(InvestmentFund investmentFund);
    List<InvestmentFund> findByIdUser(String idUser);
}

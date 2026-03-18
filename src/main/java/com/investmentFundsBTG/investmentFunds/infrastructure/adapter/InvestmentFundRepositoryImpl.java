package com.investmentFundsBTG.investmentFunds.infrastructure.adapter;

import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.gateways.InvestmentFundRepository;
import com.investmentFundsBTG.investmentFunds.infrastructure.entity.InvestmentFundEntity;
import com.investmentFundsBTG.investmentFunds.infrastructure.mapper.InvestmentFundMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InvestmentFundRepositoryImpl implements InvestmentFundRepository {

    private final InvestmentFundMongoRepository investmentFundMongoRepository;
    private final InvestmentFundMapper investmentFundMapper;

    public InvestmentFundRepositoryImpl(InvestmentFundMongoRepository investmentFundMongoRepository, InvestmentFundMapper investmentFundMapper) {
        this.investmentFundMongoRepository = investmentFundMongoRepository;
        this.investmentFundMapper = investmentFundMapper;
    }

    @Override
    public InvestmentFund byId(String id) {
        return investmentFundMongoRepository.findById(id)
                .map(investmentFundMapper::toInvestmentFund)
                .orElse(null);
    }

    @Override
    public InvestmentFund save(InvestmentFund investmentFund) {
        return investmentFundMapper.toInvestmentFund(investmentFundMongoRepository.save(investmentFundMapper.toInvestmentFundEntity(investmentFund)));
    }

    @Override
    public List<InvestmentFund> findByIdUser(String idUser) {
        return investmentFundMongoRepository.findByUserId(idUser)
                .stream()
                .map(investmentFundMapper::toInvestmentFund)
                .toList();
    }


}

package com.investmentFundsBTG.investmentFunds.infrastructure.adapter;

import com.investmentFundsBTG.investmentFunds.domain.model.fund.Fund;
import com.investmentFundsBTG.investmentFunds.domain.model.fund.gateways.FundRepository;
import com.investmentFundsBTG.investmentFunds.infrastructure.mapper.FundMapper;
import org.springframework.stereotype.Repository;

@Repository
public class FundRepositoryImpl implements FundRepository {

    private final FundMongoRepository fundMongoRepository;
    private final FundMapper fundMapper;

    public FundRepositoryImpl(FundMongoRepository fundMongoRepository, FundMapper fundMapper) {
        this.fundMongoRepository = fundMongoRepository;
        this.fundMapper = fundMapper;
    }

    @Override
    public Fund getById(String id) {
        return fundMongoRepository.findById(id)
                .map(fundMapper::toFund)
                .orElse(null);
    }

    @Override
    public Fund saveFund(Fund fund) {
        return fundMapper.toFund(fundMongoRepository.save(fundMapper.toFundEntity(fund)));
    }
}

package com.investmentFundsBTG.investmentFunds.infrastructure.adapter;

import com.investmentFundsBTG.investmentFunds.infrastructure.entity.InvestmentFundEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface InvestmentFundMongoRepository extends MongoRepository<InvestmentFundEntity,Integer> {
}

package com.investmentFundsBTG.investmentFunds.infrastructure.adapter;

import com.investmentFundsBTG.investmentFunds.infrastructure.entity.InvestmentFundEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface InvestmentFundMongoRepository extends MongoRepository<InvestmentFundEntity,String> {
    List<InvestmentFundEntity> findByUserId(String userId);
}

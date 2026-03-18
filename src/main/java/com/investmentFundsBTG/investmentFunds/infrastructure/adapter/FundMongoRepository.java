package com.investmentFundsBTG.investmentFunds.infrastructure.adapter;

import com.investmentFundsBTG.investmentFunds.infrastructure.entity.FundEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FundMongoRepository extends MongoRepository<FundEntity,String> {
}

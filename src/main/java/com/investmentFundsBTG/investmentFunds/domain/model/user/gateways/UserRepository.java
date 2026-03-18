package com.investmentFundsBTG.investmentFunds.domain.model.user.gateways;

import com.investmentFundsBTG.investmentFunds.domain.model.user.User;

public interface UserRepository {
    User getById(String id);
    User saveUser(User user);
    User findByEmail(String email);
}

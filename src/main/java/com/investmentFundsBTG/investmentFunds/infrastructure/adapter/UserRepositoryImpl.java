package com.investmentFundsBTG.investmentFunds.infrastructure.adapter;

import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import com.investmentFundsBTG.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFundsBTG.investmentFunds.infrastructure.mapper.UserMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserMongoRepository userMongoRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(UserMongoRepository userMongoRepository, UserMapper userMapper) {
        this.userMongoRepository = userMongoRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User getById(String id) {
        return userMongoRepository.findById(id)
                .map(userMapper::toUser)
                .orElse(null);
    }

    @Override
    public User saveUser(User user) {
        return userMapper.toUser(userMongoRepository.save(userMapper.toUserEntity(user)));
    }

    @Override
    public User findByEmail(String email) {
        return userMapper.toUser(userMongoRepository.findByEmail(email));
    }
}

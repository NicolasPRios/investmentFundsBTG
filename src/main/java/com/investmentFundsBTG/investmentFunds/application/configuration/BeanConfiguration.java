package com.investmentFundsBTG.investmentFunds.application.configuration;

import com.investmentFundsBTG.investmentFunds.domain.model.fund.gateways.FundRepository;
import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.gateways.InvestmentFundRepository;
import com.investmentFundsBTG.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFundsBTG.investmentFunds.domain.usecase.email.EmailService;
import com.investmentFundsBTG.investmentFunds.domain.usecase.FundUseCase;
import com.investmentFundsBTG.investmentFunds.domain.usecase.InvestmentFundUseCase;
import com.investmentFundsBTG.investmentFunds.domain.usecase.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfiguration {

    @Bean
    public InvestmentFundUseCase investmentFundUseCase(InvestmentFundRepository investmentFundRepository, FundRepository fundRepository, UserRepository userRepository, EmailService emailService){
        return new InvestmentFundUseCase(investmentFundRepository,fundRepository,userRepository,emailService);
    }

    @Bean
    public FundUseCase fundUseCase(FundRepository fundRepository){
        return new FundUseCase(fundRepository);
    }

    @Bean
    public UserUseCase userUseCase(UserRepository userRepository, PasswordEncoder  passwordEncoder){
        return new UserUseCase(userRepository, passwordEncoder);
    }
}

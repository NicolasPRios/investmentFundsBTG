package com.investmentFundsBTG.investmentFunds.domain.usecase.auth;

import com.investmentFundsBTG.investmentFunds.domain.model.user.User;
import com.investmentFundsBTG.investmentFunds.domain.model.user.gateways.UserRepository;
import com.investmentFundsBTG.investmentFunds.domain.usecase.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return jwtService.generateToken(user);
    }
}

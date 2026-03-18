package com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints;

import com.investmentFundsBTG.investmentFunds.domain.model.investmentfund.InvestmentFund;
import com.investmentFundsBTG.investmentFunds.domain.usecase.InvestmentFundUseCase;
import com.investmentFundsBTG.investmentFunds.infrastructure.entrypoints.DTO.InvestmentFundDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class InvestmentFundController {

    private final InvestmentFundUseCase investmentFundUseCase;


    public InvestmentFundController(InvestmentFundUseCase investmentFundUseCase) {
        this.investmentFundUseCase = investmentFundUseCase;
    }

    @GetMapping
    public ResponseEntity<Iterable<InvestmentFund>> allInvestmentFundsByIdUser(@RequestParam String id){
        return ResponseEntity.ok().body(investmentFundUseCase.findByIduser(id));
    }

    @PostMapping
    public ResponseEntity<InvestmentFund> createInvestmentFund(@Validated @RequestBody InvestmentFundDTO investmentFundDTO){
            return ResponseEntity.ok().body(investmentFundUseCase.save(investmentFundDTO));
    }

    @PutMapping
    public ResponseEntity<InvestmentFund> CancelSubscription(@RequestParam String id){
        return ResponseEntity.ok().body(investmentFundUseCase.cancelSubscription(id));
    }
}

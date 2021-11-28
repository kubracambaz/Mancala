package com.bol.mancalagame.config;

import com.bol.mancalagame.rules.LastStoneEmptyPitRule;
import com.bol.mancalagame.rules.TreasuryPitRule;
import org.jeasy.rules.api.Rules;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RuleManager {

    @Bean
    public Rules rules(){
        Rules rules = new Rules();
        rules.register(new LastStoneEmptyPitRule());
        rules.register(new TreasuryPitRule());
        return rules;
    }
}

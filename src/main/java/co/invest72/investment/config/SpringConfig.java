package co.invest72.investment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.application.CalculateGoalInvestment;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.application.InvestmentFactory;

@Configuration
public class SpringConfig {
	@Bean
	public CalculateExpirationInvestment calculateExpirationInvestment(InvestmentFactory factory) {
		return new CalculateExpirationInvestment(factory);
	}

	@Bean
	public CalculateMonthlyInvestment calculateMonthlyInvestment(InvestmentFactory factory) {
		return new CalculateMonthlyInvestment(factory);
	}

	@Bean
	public CalculateGoalInvestment calculateGoalInvestment(InvestmentFactory factory) {
		return new CalculateGoalInvestment();
	}

	@Bean
	public InvestmentFactory investmentFactory() {
		return new InvestmentFactory();
	}
}

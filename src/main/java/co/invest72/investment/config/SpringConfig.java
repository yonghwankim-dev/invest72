package co.invest72.investment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.invest72.investment.application.CalculateExpirationInvestment;
import co.invest72.investment.application.CalculateMonthlyCompoundInterest;
import co.invest72.investment.application.CalculateMonthlyInvestment;
import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.application.TaxPercentFormatter;

@Configuration
public class SpringConfig {
	@Bean
	public CalculateExpirationInvestment calculateExpirationInvestment(InvestmentFactory factory) {
		return new CalculateExpirationInvestment(factory, taxPercentFormatter());
	}

	@Bean
	public CalculateMonthlyInvestment calculateMonthlyInvestment(InvestmentFactory factory) {
		return new CalculateMonthlyInvestment(factory);
	}

	@Bean
	public CalculateMonthlyCompoundInterest calculateGoalInvestment(InvestmentFactory factory) {
		return new CalculateMonthlyCompoundInterest();
	}

	@Bean
	public InvestmentFactory investmentFactory() {
		return new InvestmentFactory();
	}

	@Bean
	public TaxPercentFormatter taxPercentFormatter() {
		return new TaxPercentFormatter();
	}
}

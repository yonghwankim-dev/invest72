package co.invest72.investment.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.amount.FixedDepositAmount;
import co.invest72.investment.domain.amount.MonthlyAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.period.YearlyInvestPeriod;
import co.invest72.investment.domain.tax.KoreanTaxableFactory;

class MonthlyCompoundInterestTest {
	
	@Test
	void canCreated() {
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Investment investment = MonthlyCompoundInterest.builder()
			.initialAmount(new FixedDepositAmount(0))
			.monthlyAmount(new MonthlyAmount(1_000_000))
			.investPeriod(new YearlyInvestPeriod(1))
			.interestRate(new AnnualInterestRate(0.05))
			.taxable(taxableFactory.createNonTax())
			.build();

		Assertions.assertThat(investment).isNotNull();
	}

}

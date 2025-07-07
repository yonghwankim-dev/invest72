package domain.investment;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodYearRange;
import domain.tax.factory.KoreanTaxableFactory;

class MonthlyInvestmentTest {

	@Test
	void created() {
		MonthlyInvestment monthlyInvestment = new SimpleFixedDeposit(
			new FixedDepositAmount(1_000_000),
			new MonthBasedRemainingPeriodProvider(new PeriodYearRange(1)),
			new AnnualInterestRate(0.05),
			new KoreanTaxableFactory().createNonTax()
		);
		Assertions.assertNotNull(monthlyInvestment);
	}
}

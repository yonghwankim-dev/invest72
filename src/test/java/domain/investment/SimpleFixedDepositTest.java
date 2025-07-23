package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.amount.FixedDepositAmount;
import domain.amount.LumpSumInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodRange;
import domain.invest_period.PeriodYearRange;
import domain.invest_period.RemainingPeriodProvider;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class SimpleFixedDepositTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		LumpSumInvestmentAmount investmentAmount = new FixedDepositAmount(1_000_000);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		InterestRate interestRate = new AnnualInterestRate(0.05);
		PeriodRange periodRange = new PeriodYearRange(1);
		RemainingPeriodProvider remainingPeriodProvider = new MonthBasedRemainingPeriodProvider(periodRange);
		investment = new SimpleFixedDeposit(investmentAmount, remainingPeriodProvider, interestRate, taxable);
	}

	@Test
	void created() {
		assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsSimple() {
		int amount = investment.getTotalProfit();

		int expectedAmount = 1_050_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnPrincipal() {
		int month = 12;

		int principal = investment.getPrincipal(month);

		int expectedPrincipal = 1_000_000;
		assertEquals(expectedPrincipal, principal);
	}

	@Test
	void shouldReturnInterest() {
		assertEquals(50_000, investment.getInterest());
		assertEquals(50_000, investment.getInterest(12));
	}

	@Test
	void shouldReturnTax_whenTaxIsNonTax() {
		assertEquals(0, investment.getTax(12));
	}
}

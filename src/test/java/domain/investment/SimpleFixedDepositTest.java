package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthBasedRemainingPeriodProvider;
import domain.invest_period.PeriodRange;
import domain.invest_period.PeriodYearRange;
import domain.invest_period.RemainingPeriodProvider;
import domain.invest_period.YearlyInvestPeriod;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class SimpleFixedDepositTest {

	private Investment investment;
	private LumpSumInvestmentAmount investmentAmount;
	private Taxable taxable;
	private InterestRate interestRate;
	private InvestPeriod investPeriod;

	@BeforeEach
	void setUp() {
		investmentAmount = new FixedDepositAmount(1_000_000);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		interestRate = new AnnualInterestRate(0.05);
		investPeriod = new YearlyInvestPeriod(1);
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
		int amount = investment.getAmount();

		int expectedAmount = 1_050_000;
		assertEquals(expectedAmount, amount);
	}
}

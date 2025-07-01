package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.LumpSumInvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
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
		investment = new SimpleFixedDeposit(investmentAmount, investPeriod, interestRate, taxable);
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

	@Test
	void shouldThrowException_whenNotInvestmentAmountInstanceOfLumpSumInvestmentAmount() {
		InvestmentAmount invalidInvestmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		assertThrows(ClassCastException.class, () -> investment.getAmount(invalidInvestmentAmount));
	}
}

package domain.investment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.MonthlyInvestPeriod;
import domain.investment.Investment;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class SimpleFixedInstallmentSavingTest {

	private Investment investment;
	private InstallmentInvestmentAmount investmentAmount;
	private InvestPeriod investPeriod;
	private InterestRate annualInterestRateRate;
	private TaxableFactory taxableFactory;
	private Taxable taxable;

	@BeforeEach
	void setUp() {
		investmentAmount = new MonthlyInstallmentInvestmentAmount(1_000_000);
		investPeriod = new MonthlyInvestPeriod(12);
		annualInterestRateRate = new AnnualInterestRate(0.05);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);
	}

	@Test
	void created(){
		assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount(){
		int amount = investment.getAmount();

		int expectedAmount = 12_325_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInvestAmountInstanceOfMonthlyInstallmentInvestmentAmount(){
		investmentAmount = new YearlyInstallmentInvestmentAmount(12_000_000);
		investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			annualInterestRateRate,
			taxable
		);

		int amount = investment.getAmount();

		int expectedAmount = 12_325_000;
		assertEquals(expectedAmount, amount);
	}
}

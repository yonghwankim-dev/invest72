import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FixedInstallmentSavingsPlanTest {

	private Investment investment;

	@BeforeEach
	void setUp() {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		InvestPeriod investPeriod = new MonthlyInvestPeriod(12);
		InterestRate annualInterestRateRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createNonTax();
		Interest interest = new CompoundInterest(investmentAmount, investPeriod, annualInterestRateRate, taxable);
		investment = new FixedInstallmentSavingsPlan(interest);
	}

	@Test
	void created(){
		assertNotNull(investment);
	}

	@Test
	void shouldReturnAmount(){
		int amount = investment.getAmount();

		int expectedAmount = 12_330_017;
		assertEquals(expectedAmount, amount);
	}
}

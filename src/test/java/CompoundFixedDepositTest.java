import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompoundFixedDepositTest {

	private LumpSumInvestmentAmount depositAmount;
	private InterestRate interestRate;
	private InvestPeriod investPeriod;
	private TaxableFactory taxableFactory;
	private Taxable taxable;
	private Investment investment;

	@BeforeEach
	void setUp() {
		depositAmount = new FixedDepositAmount(1_000_000);
		interestRate = new AnnualInterestRate(0.05);
		investPeriod = new YearlyInvestPeriod(1);
		taxableFactory = new KoreanTaxableFactory();
		taxable = taxableFactory.createNonTax();
		investment = new CompoundFixedDeposit(
			depositAmount,
			interestRate,
			investPeriod,
			taxable
		);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsCompound() {
		int amount = investment.getAmount();

		int expectedAmount = 1_051_162;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnAmount_whenInterestRateIsCompoundAndStandardTax() {
		taxable = taxableFactory.createStandardTax();
		investment = new CompoundFixedDeposit(
			depositAmount,
			interestRate,
			investPeriod,
			taxable
		);
		int amount = investment.getAmount();

		int expectedAmount = 1_043_283;
		assertEquals(expectedAmount, amount);
	}
}

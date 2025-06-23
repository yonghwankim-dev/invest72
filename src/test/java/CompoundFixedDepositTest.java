import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CompoundFixedDepositTest {

	@Test
	void shouldReturnAmount_whenInterestRateIsCompound() {
		Investment investment = new CompoundFixedDeposit(
				new FixedDepositAmount(1_000_000),
				new AnnualInterestRate(0.05),
				new YearlyInvestPeriod(1),
				new KoreanTaxableFactory().createNonTax()
		);
		int amount = investment.getAmount();

		int expectedAmount = 1_051_162;
		assertEquals(expectedAmount, amount);
	}
}

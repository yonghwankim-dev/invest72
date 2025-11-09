package co.invest72.investment.domain.amount;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.LumpSumInvestmentAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;
import co.invest72.investment.domain.InterestRate;
import testutil.BigDecimalAssertion;

class FixedDepositAmountTest {

	private LumpSumInvestmentAmount investmentAmount;

	@BeforeEach
	void setUp() {
		investmentAmount = new FixedDepositAmount(1_000_000);
	}

	@Test
	void created() {
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldThrowException_whenAmountIsNegative() {
		assertThrows(IllegalArgumentException.class, () -> new FixedDepositAmount(-1));
	}

	@Test
	void shouldReturnDepositAmount() {
		int depositAmount = investmentAmount.getDepositAmount();

		int expectedDepositAmount = 1_000_000;
		assertEquals(expectedDepositAmount, depositAmount);
	}

	@Test
	void shouldReturnInterest() {
		InterestRate interestRate = new AnnualInterestRate(0.05);

		double interest = investmentAmount.calAnnualInterest(interestRate);

		double expectedInterest = 50_000;
		assertEquals(expectedInterest, interest, 0.001);
	}

	@Test
	void calMonthlyInterest_shouldReturnMonthlyInterest() {
		InterestRate interestRate = new AnnualInterestRate(0.05);

		BigDecimal interest = investmentAmount.calMonthlyInterest(interestRate);

		BigDecimal expectedInterest = BigDecimal.valueOf(4166.666666666667000000);
		BigDecimalAssertion.assertBigDecimalEquals(expectedInterest, interest);
	}
}

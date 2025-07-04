package domain.invest_amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.LumpSumInvestmentAmount;

class FixedDepositAmountTest {

	private LumpSumInvestmentAmount investmentAmount;

	@BeforeEach
	void setUp() {
		investmentAmount = new FixedDepositAmount(1_000_000);
	}

	@Test
	void created(){
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
	void shouldReturnInterest(){
		InterestRate interestRate = new AnnualInterestRate(0.05);

		double interest = investmentAmount.calAnnualInterest(interestRate);

		double expectedInterest = 50_000;
		assertEquals(expectedInterest, interest, 0.001);
	}
}

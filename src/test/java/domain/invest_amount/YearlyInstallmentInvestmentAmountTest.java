package domain.invest_amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;

class YearlyInstallmentInvestmentAmountTest {

	private InstallmentInvestmentAmount investmentAmount;

	@BeforeEach
	void setUp() {
		investmentAmount = new YearlyInstallmentInvestmentAmount(12_000_000);
	}

	@Test
	void created(){
		assertNotNull(investmentAmount);
	}

	@Test
	void shouldReturnAmount(){
		int amount = investmentAmount.getMonthlyAmount();

		int expectedAmount = 1_000_000;
		assertEquals(expectedAmount, amount);
	}

	@Test
	void shouldReturnInterest(){
		InterestRate interestRate = new AnnualInterestRate(0.05);

		double interest = investmentAmount.calAnnualInterest(interestRate);

		double expectedInterest = 600_000;
		assertEquals(expectedInterest, interest, 0.001);
	}
}

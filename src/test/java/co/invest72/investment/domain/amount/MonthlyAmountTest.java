package co.invest72.investment.domain.amount;

import java.math.BigDecimal;

import org.assertj.core.api.Assertions;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestmentAmount;
import co.invest72.investment.domain.interest.AnnualInterestRate;

class MonthlyAmountTest {

	@Test
	void canCreated() {
		InvestmentAmount investmentAmount = new MonthlyAmount(1_000_000);

		Assertions.assertThat(investmentAmount).isNotNull();
	}

	@Test
	void calMonthlyInterest() {
		InvestmentAmount investmentAmount = new MonthlyAmount(1_000_000);
		InterestRate interestRate = new AnnualInterestRate(0.05);

		BigDecimal actual = investmentAmount.calMonthlyInterest(interestRate);

		BigDecimal expected = new BigDecimal("4166.66");
		Assertions.assertThat(actual).isCloseTo(expected, Percentage.withPercentage(0.01));
	}
}

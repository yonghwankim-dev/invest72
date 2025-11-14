package co.invest72.investment.domain.amount;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import co.invest72.investment.domain.InvestmentAmount;

class MonthlyAmountTest {

	@Test
	void canCreated() {
		InvestmentAmount investmentAmount = new MonthlyAmount(1_000_000);

		Assertions.assertThat(investmentAmount).isNotNull();
	}

}

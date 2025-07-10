package domain.invest_amount;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MonthlyInvestmentAmountTest {

	@Test
	void created() {
		InvestmentAmount investmentAmount = new MonthlyInvestmentAmount(1_000_000);
		assertNotNull(investmentAmount);
	}
}

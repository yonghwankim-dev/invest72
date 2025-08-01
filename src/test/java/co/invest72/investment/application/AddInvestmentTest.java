package co.invest72.investment.application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AddInvestmentTest {

	@Test
	void canCreated() {
		AddInvestment addInvestment = new AddInvestment();
		Assertions.assertNotNull(addInvestment);
	}
}

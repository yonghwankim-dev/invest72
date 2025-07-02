package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class InvestmentUseCaseFactoryTest {

	@Test
	void created() {
		InvestmentUseCaseFactory factory = new InvestmentUseCaseFactory();
		Assertions.assertNotNull(factory);
	}
}

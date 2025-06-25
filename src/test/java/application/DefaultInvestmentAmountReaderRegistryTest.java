package application;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DefaultInvestmentAmountReaderRegistryTest {

	@Test
	void created() {
		InvestmentAmountReaderRegistry registry = new DefaultInvestmentAmountReaderRegistry(System.out);
		assertNotNull(registry);
	}

}

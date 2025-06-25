package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import adapter.console.reader.InvestmentAmountReader;

class DefaultInvestmentAmountReaderRegistryTest {

	@Test
	void created() {
		InvestmentAmountReaderRegistry registry = new DefaultInvestmentAmountReaderRegistry(System.out);
		assertNotNull(registry);
	}

	@Test
	void getReaders() {
		InvestmentAmountReaderRegistry registry = new DefaultInvestmentAmountReaderRegistry(System.out);

		List<InvestmentAmountReader> readers = registry.getReaders();

		assertNotNull(readers);
		assertEquals(readers.size(), 2);
	}
}

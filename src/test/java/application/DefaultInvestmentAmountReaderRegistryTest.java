package application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import adapter.console.reader.FixedDepositAmountReader;
import adapter.console.reader.InvestmentAmountReader;
import domain.type.InvestmentType;

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

	@Test
	void shouldReturnInvestmentAmountReader_whenInvestmentTypeIsFixedDeposit() {
		InvestmentAmountReaderRegistry registry = new DefaultInvestmentAmountReaderRegistry(System.out);
		InvestmentType investmentType = InvestmentType.FIXED_DEPOSIT;

		InvestmentAmountReader reader = registry.getReaderBy(investmentType);

		assertInstanceOf(FixedDepositAmountReader.class, reader);
	}
}

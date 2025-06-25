package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FixedDepositAmountReaderTest {

	@Test
	void created() {
		InvestmentAmountReader reader = new FixedDepositAmountReader();

		assertNotNull(reader);
	}
}

package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class InstallmentInvestmentAmountReaderTest {

	@Test
	void created() {
		InvestmentAmountReader reader = new InstallmentInvestmentAmountReader(System.out);

		assertNotNull(reader);
	}
}

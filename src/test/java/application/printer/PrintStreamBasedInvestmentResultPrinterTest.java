package application.printer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrintStreamBasedInvestmentResultPrinterTest {

	@Test
	void created() {
		InvestmentResultPrinter printer = new PrintStreamBasedInvestmentResultPrinter(System.out);

		Assertions.assertNotNull(printer);
	}

}

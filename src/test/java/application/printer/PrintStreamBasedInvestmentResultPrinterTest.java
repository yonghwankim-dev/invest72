package application.printer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PrintStreamBasedInvestmentResultPrinterTest {

	@Test
	void created() {
		InvestmentResultPrinter printer = new PrintStreamBasedInvestmentResultPrinter(System.out);

		Assertions.assertNotNull(printer);
	}

	@Test
	void printTotalPrincipal() {
		OutputStream outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		InvestmentResultPrinter printer = new PrintStreamBasedInvestmentResultPrinter(printStream);

		printer.printTotalPrincipal(12_000_000);

		String output = outputStream.toString();
		Assertions.assertEquals("total principal amount: 12,000,000원\n", output);
	}
}

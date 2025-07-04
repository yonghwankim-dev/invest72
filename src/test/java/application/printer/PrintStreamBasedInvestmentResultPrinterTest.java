package application.printer;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PrintStreamBasedInvestmentResultPrinterTest {

	private InvestmentResultPrinter printer;
	private OutputStream outputStream;

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		PrintStream printStream = new PrintStream(outputStream);
		printer = new PrintStreamBasedInvestmentResultPrinter(printStream);
	}

	@Test
	void printTotalPrincipal() {
		printer.printTotalPrincipal(12_000_000);

		String output = outputStream.toString();
		Assertions.assertEquals("total principal amount: 12,000,000원\n", output);
	}

	@Test
	void printInterest() {
		printer.printInterest(330_017);

		String output = outputStream.toString();
		Assertions.assertEquals("total interest amount: 330,017원\n", output);
	}

	@Test
	void printTax_shouldMinus_whenTaxIsPositive() {
		printer.printTax(50_823);

		String output = outputStream.toString();
		Assertions.assertEquals("total tax amount: -50,823원\n", output);
	}
}

package application.reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;

class CalculateInvestmentRequestReaderTest {

	private CalculateInvestmentRequestReader calculateInvestmentRequestReader;
	private GuidePrinter guidePrinter;

	private BufferedReader newBufferedReader(String... texts) {
		String input = String.join(System.lineSeparator(), texts);
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	private void setCalculateInvestmentRequestReader(BufferedReader bufferedReader) {
		calculateInvestmentRequestReader = new CalculateInvestmentRequestReader(bufferedReader, guidePrinter);
	}

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out, StandardCharsets.UTF_8);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter);
	}

	@Test
	void readInvestmentType() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("예금");
		setCalculateInvestmentRequestReader(bufferedReader);

		String investmentType = calculateInvestmentRequestReader.readInvestmentType();

		Assertions.assertEquals("예금", investmentType);
	}

	@Test
	void readPeriodType() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("년");
		setCalculateInvestmentRequestReader(bufferedReader);

		String periodType = calculateInvestmentRequestReader.readPeriodType();

		Assertions.assertEquals("년", periodType);
	}

	@Test
	void readPeriod() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("10");
		setCalculateInvestmentRequestReader(bufferedReader);

		int period = calculateInvestmentRequestReader.readPeriod();

		Assertions.assertEquals(10, period);
	}

	@Test
	void readInterestType() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("복리");
		setCalculateInvestmentRequestReader(bufferedReader);

		String interestType = calculateInvestmentRequestReader.readInterestType();

		Assertions.assertEquals("복리", interestType);
	}

	@Test
	void readInterestRate() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("5");
		setCalculateInvestmentRequestReader(bufferedReader);

		double interestRate = calculateInvestmentRequestReader.readInterestRate();

		Assertions.assertEquals(0.05, interestRate);
	}
}

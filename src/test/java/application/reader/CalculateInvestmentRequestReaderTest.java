package application.reader;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CalculateInvestmentRequestReaderTest {

	private CalculateInvestmentRequestReader calculateInvestmentRequestReader;

	private BufferedReader newBufferedReader(String... texts) {
		String input = String.join(System.lineSeparator(), texts);
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	@Test
	void readInvestmentType() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("10000000");
		calculateInvestmentRequestReader = new CalculateInvestmentRequestReader(bufferedReader);

		String investmentType = calculateInvestmentRequestReader.readInvestmentType();

		Assertions.assertEquals("10000000", investmentType);
	}

	@Test
	void readPeriodType() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("년");
		calculateInvestmentRequestReader = new CalculateInvestmentRequestReader(bufferedReader);

		String periodType = calculateInvestmentRequestReader.readPeriodType();

		Assertions.assertEquals("년", periodType);
	}

	@Test
	void readPeriod() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("10");
		calculateInvestmentRequestReader = new CalculateInvestmentRequestReader(bufferedReader);

		int period = calculateInvestmentRequestReader.readPeriod();

		Assertions.assertEquals(10, period);
	}

	@Test
	void readInterestType() throws IOException {
		BufferedReader bufferedReader = newBufferedReader("복리");
		calculateInvestmentRequestReader = new CalculateInvestmentRequestReader(bufferedReader);

		String interestType = calculateInvestmentRequestReader.readInterestType(bufferedReader);

		Assertions.assertEquals("복리", interestType);
	}
}

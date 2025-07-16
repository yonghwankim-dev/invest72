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

	@Test
	void readInvestmentType() throws IOException {
		String input = String.join(System.lineSeparator(), "10000000");
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
		CalculateInvestmentRequestReader calculateInvestmentRequestReader = new CalculateInvestmentRequestReader(
			bufferedReader);

		String investmentType = calculateInvestmentRequestReader.readInvestmentType();

		Assertions.assertEquals("10000000", investmentType);
	}

}

package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.invest_amount.InvestmentAmount;

class FixedDepositAmountReaderTest {

	private InvestmentAmountReader reader;

	@BeforeEach
	void setUp() {
		reader = new FixedDepositAmountReader(System.out);
	}

	@Test
	void created() {
		assertNotNull(reader);
	}

	@Test
	void shouldReturnFixedDepositAmount_whenReaderIsFixedDepositAmountReaderType() throws IOException {
		String input = String.join(System.lineSeparator(),
			"1000000"
		);
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		InvestmentAmount investmentAmount = reader.read(bufferedReader);

		assertInstanceOf(domain.invest_amount.FixedDepositAmount.class, investmentAmount);
	}
}

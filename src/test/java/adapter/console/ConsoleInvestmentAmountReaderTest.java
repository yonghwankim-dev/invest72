package adapter.console;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

class ConsoleInvestmentAmountReaderTest {

	@Test
	void created() {
		InvestmentAmountReader reader = new ConsoleInvestmentAmountReader(System.out);

		assertNotNull(reader);
	}

	@Test
	void shouldReturnFixedDepositAmount_whenInvestmentTypeIsFixedDeposit() throws IOException {
		InvestmentAmountReader reader = new ConsoleInvestmentAmountReader(System.out);
		String input = String.join(System.lineSeparator(),
			"1000000"
		);
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		InvestmentAmount investmentAmount = reader.read(InvestmentType.FIXED_DEPOSIT, bufferedReader);

		assertInstanceOf(FixedDepositAmount.class, investmentAmount);
	}

}

package application.reader.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.reader.InvestmentAmountReader;
import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

class FixedDepositAmountReaderTest {

	private InvestmentAmountReader reader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter);
		reader = new FixedDepositAmountReader(guidePrinter);
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

	@Test
	void shouldReturnTrue_whenInvestmentTypeIsFixedDeposit() {
		InvestmentType investmentType = InvestmentType.FIXED_DEPOSIT;

		boolean supports = reader.supports(investmentType);

		assertTrue(supports);
	}

	@Test
	void shouldReturnFalse_whenInvestmentTypeIsNotFixedDeposit() {
		InvestmentType investmentType = InvestmentType.INSTALLMENT_SAVING;

		boolean supports = reader.supports(investmentType);

		assertFalse(supports);
	}
}

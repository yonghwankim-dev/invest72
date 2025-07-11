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
import application.reader.InvestmentAmountReader;
import domain.amount.InvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.type.InvestmentType;

class InstallmentInvestmentAmountReaderTest {

	private InvestmentAmountReader reader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		BufferedWriterBasedGuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter);
		reader = new InstallmentInvestmentAmountReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(reader);
	}

	@Test
	void shouldReturnInstallmentInvestmentAmount_whenReaderIsInstallmentInvestmentAmountReaderType() throws
		IOException {
		String input = String.join(System.lineSeparator(),
			"월 1000000"
		);
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

		InvestmentAmount investmentAmount = reader.read(bufferedReader);

		assertInstanceOf(MonthlyInstallmentInvestmentAmount.class, investmentAmount);
	}

	@Test
	void shouldReturnTrue_whenInvestmentTypeIsInstallmentSaving() {
		InvestmentType investmentType = InvestmentType.INSTALLMENT_SAVING;

		boolean supports = reader.supports(investmentType);

		assertTrue(supports);
	}

	@Test
	void shouldReturnFalse_whenInvestmentTypeIsNotInstallmentSaving() {
		InvestmentType investmentType = InvestmentType.FIXED_DEPOSIT;

		boolean supports = reader.supports(investmentType);

		assertFalse(supports);
	}
}

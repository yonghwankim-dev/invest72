package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.type.InvestmentType;

class InstallmentInvestmentAmountReaderTest {

	@Test
	void created() {
		InvestmentAmountReader reader = new InstallmentInvestmentAmountReader(System.out);

		assertNotNull(reader);
	}

	@Test
	void shouldReturnInstallmentInvestmentAmount_whenReaderIsInstallmentInvestmentAmountReaderType() throws
		IOException {
		InvestmentAmountReader reader = new InstallmentInvestmentAmountReader(System.out);

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
		InvestmentAmountReader reader = new InstallmentInvestmentAmountReader(System.out);
		InvestmentType investmentType = InvestmentType.INSTALLMENT_SAVING;

		boolean supports = reader.supports(investmentType);

		assertTrue(supports);
	}

	@Test
	void shouldReturnFalse_whenInvestmentTypeIsNotInstallmentSaving() {
		InvestmentAmountReader reader = new InstallmentInvestmentAmountReader(System.out);
		InvestmentType investmentType = InvestmentType.FIXED_DEPOSIT;

		boolean supports = reader.supports(investmentType);

		assertFalse(supports);
	}
}

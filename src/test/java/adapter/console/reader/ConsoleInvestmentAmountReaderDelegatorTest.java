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

import domain.invest_amount.FixedDepositAmount;
import domain.invest_amount.InvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_amount.YearlyInstallmentInvestmentAmount;
import domain.type.InvestmentType;

class ConsoleInvestmentAmountReaderDelegatorTest {

	private InvestmentAmountReaderDelegator reader;
	private String input;
	private BufferedReader bufferedReader;

	private InputStream toInputStream(String text) {
		return new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
	}

	private BufferedReader newBufferedReader(InputStream inputStream) {
		return new BufferedReader(new InputStreamReader(inputStream));
	}

	@BeforeEach
	void setUp() {
		reader = new ConsoleInvestmentAmountReaderDelegator(System.out);
	}

	@Test
	void created() {
		assertNotNull(reader);
	}

	@Test
	void shouldReturnFixedDepositAmount_whenInvestmentTypeIsFixedDeposit() throws IOException {
		input = String.join(System.lineSeparator(),
			"1000000"
		);
		bufferedReader = newBufferedReader(toInputStream(input));

		InvestmentAmount investmentAmount = reader.read(InvestmentType.FIXED_DEPOSIT, bufferedReader);

		assertInstanceOf(FixedDepositAmount.class, investmentAmount);
	}

	@Test
	void shouldReturnMonthlyInstallmentInvestmentAmount_whenInvestmentTypeIsMonthlyInstallment() throws IOException {
		input = String.join(System.lineSeparator(),
			"월 1000000"
		);
		bufferedReader = newBufferedReader(toInputStream(input));

		InvestmentAmount investmentAmount = reader.read(InvestmentType.INSTALLMENT_SAVING, bufferedReader);

		assertInstanceOf(MonthlyInstallmentInvestmentAmount.class, investmentAmount);
	}

	@Test
	void shouldReturnYearlyInstallmentInvestmentAmount_whenInvestmentTypeIsYearlyInstallment() throws IOException {
		input = String.join(System.lineSeparator(),
			"년 12000000"
		);
		bufferedReader = newBufferedReader(toInputStream(input));

		InvestmentAmount investmentAmount = reader.read(InvestmentType.INSTALLMENT_SAVING, bufferedReader);

		assertInstanceOf(YearlyInstallmentInvestmentAmount.class, investmentAmount);
	}

	@Test
	void shouldThrowException_whenPartsLengthIsNotTwo() {
		input = String.join(System.lineSeparator(),
			"월 1000000 2000000"
		);
		bufferedReader = newBufferedReader(toInputStream(input));

		assertThrows(IllegalArgumentException.class, () -> {
			reader.read(InvestmentType.INSTALLMENT_SAVING, bufferedReader);
		});
	}

	@Test
	void shouldThrowException_whenPeriodTypeIsInvalid() {
		input = String.join(System.lineSeparator(),
			"일 1000000"
		);
		bufferedReader = newBufferedReader(toInputStream(input));

		assertThrows(IllegalArgumentException.class, () -> {
			reader.read(InvestmentType.INSTALLMENT_SAVING, bufferedReader);
		});
	}
}

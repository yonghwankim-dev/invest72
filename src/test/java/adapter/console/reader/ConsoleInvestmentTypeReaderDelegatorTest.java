package adapter.console.reader;

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

import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;
import domain.type.InvestmentType;

class ConsoleInvestmentTypeReaderDelegatorTest {

	private InvestmentTypeReaderDelegator delegator;
	private String input;
	private InputStream in;
	private BufferedReader reader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		delegator = new ConsoleInvestmentTypeReaderDelegator(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(delegator);
	}

	@Test
	void shouldReturnFixedDeposit_whenTextIsFixedDeposit() throws IOException {
		input = "예금";
		in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		reader = new BufferedReader(new InputStreamReader(in));

		InvestmentType investmentType = delegator.read(reader);

		assertEquals(InvestmentType.FIXED_DEPOSIT, investmentType);
	}

	@Test
	void shouldReturnInstallmentSaving_whenTextIsInstallmentSaving() throws IOException {
		input = "적금";
		in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		reader = new BufferedReader(new InputStreamReader(in));

		InvestmentType investmentType = delegator.read(reader);

		assertEquals(InvestmentType.INSTALLMENT_SAVING, investmentType);
	}

	@Test
	void shouldThrowException_whenInputIsInvalid() {
		input = "invalid";
		in = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		reader = new BufferedReader(new InputStreamReader(in));

		assertThrows(IllegalArgumentException.class, () -> {
			delegator.read(reader);
		});
	}
}

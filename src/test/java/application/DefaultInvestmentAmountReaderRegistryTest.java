package application;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.reader.FixedDepositAmountReader;
import adapter.console.reader.InstallmentInvestmentAmountReader;
import application.reader.InvestmentAmountReader;
import adapter.console.ui.WriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import domain.type.InvestmentType;

class DefaultInvestmentAmountReaderRegistryTest {

	private InvestmentAmountReaderRegistry registry;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		registry = new DefaultInvestmentAmountReaderRegistry(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(registry);
	}

	@Test
	void getReaders() {
		List<InvestmentAmountReader> readers = registry.getReaders();

		assertNotNull(readers);
		assertEquals(readers.size(), 2);
	}

	@Test
	void shouldReturnInvestmentAmountReader_whenInvestmentTypeIsFixedDeposit() {
		InvestmentType investmentType = InvestmentType.FIXED_DEPOSIT;

		InvestmentAmountReader reader = registry.getReaderBy(investmentType);

		assertInstanceOf(FixedDepositAmountReader.class, reader);
	}

	@Test
	void shouldReturnInvestmentAmountReader_whenInvestmentTypeIsInstallmentSaving() {
		InvestmentType investmentType = InvestmentType.INSTALLMENT_SAVING;

		InvestmentAmountReader reader = registry.getReaderBy(investmentType);

		assertInstanceOf(InstallmentInvestmentAmountReader.class, reader);
	}
}

package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;

class ConsoleInvestmentTypeReaderDelegatorTest {

	@Test
	void created() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		InvestmentTypeReaderDelegator delegator = new ConsoleInvestmentTypeReaderDelegator(guidePrinter);

		assertNotNull(delegator);
	}

	@Test
	void shouldReturnFixedDeposit_whenTextIsFixedDeposit() {

	}
}

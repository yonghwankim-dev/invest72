package adapter.console.reader;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;

class ConsolePeriodTypeReaderDelegatorTest {

	@Test
	void created() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		PeriodTypeReaderDelegator periodTypeReaderDelegator = new ConsolePeriodTypeReaderDelegator(guidePrinter);

		Assertions.assertNotNull(periodTypeReaderDelegator);
	}
}

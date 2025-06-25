package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;

class ConsolePeriodTypeReaderDelegatorTest {

	private PeriodTypeReaderDelegator periodTypeReaderDelegator;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		periodTypeReaderDelegator = new ConsolePeriodTypeReaderDelegator(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(periodTypeReaderDelegator);
	}

	@Test
	void shouldReturnMonthText() throws Exception {
		String periodType = "월";
		BufferedReader reader = new BufferedReader(new StringReader(periodType));

		String result = periodTypeReaderDelegator.read(reader);

		assertEquals(periodType, result);
	}

	@Test
	void shouldReturnYearText() throws Exception {
		String periodType = "년";
		BufferedReader reader = new BufferedReader(new StringReader(periodType));

		String result = periodTypeReaderDelegator.read(reader);

		assertEquals(periodType, result);
	}
}

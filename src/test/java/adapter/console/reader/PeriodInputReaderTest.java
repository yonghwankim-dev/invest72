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

class PeriodInputReaderTest {

	private PeriodReader periodReader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		periodReader = new PeriodInputReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(periodReader);
	}

	@Test
	void shouldReturnPeriod() throws Exception {
		String input = "12";
		BufferedReader reader = new BufferedReader(new StringReader(input));
		int period = periodReader.read(reader);
		assertEquals(12, period);
	}
}

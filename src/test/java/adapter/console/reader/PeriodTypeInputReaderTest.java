package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.ui.WriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;

class PeriodTypeInputReaderTest {

	private PeriodTypeReader periodTypeReader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		periodTypeReader = new PeriodTypeInputReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(periodTypeReader);
	}

	@Test
	void shouldReturnPeriodType_whenTextIsMonth() throws Exception {
		String periodType = "월";
		BufferedReader reader = new BufferedReader(new StringReader(periodType));

		String text = periodTypeReader.read(reader);

		assertEquals("월", text);
	}

	@Test
	void shouldReturnYearText() throws Exception {
		String periodType = "년";
		BufferedReader reader = new BufferedReader(new StringReader(periodType));

		String text = periodTypeReader.read(reader);

		assertEquals("년", text);
	}
}

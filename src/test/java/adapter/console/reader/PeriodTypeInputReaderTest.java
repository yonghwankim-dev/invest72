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
import domain.type.PeriodType;

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

		PeriodType result = periodTypeReader.read(reader);

		assertEquals(PeriodType.MONTH, result);
	}

	@Test
	void shouldReturnYearText() throws Exception {
		String periodType = "년";
		BufferedReader reader = new BufferedReader(new StringReader(periodType));

		PeriodType result = periodTypeReader.read(reader);

		assertEquals(PeriodType.YEAR, result);
	}
}

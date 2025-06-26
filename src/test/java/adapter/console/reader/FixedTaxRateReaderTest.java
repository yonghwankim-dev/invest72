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

class FixedTaxRateReaderTest {

	private TaxRateReader reader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		reader = new FixedTaxRateReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(reader);
	}

	@Test
	void shouldReturnTaxRate_whenInputIsValid() throws Exception {
		String input = "10";
		BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

		double taxRate = this.reader.read(bufferedReader);

		assertEquals(0.1, taxRate);
	}
}

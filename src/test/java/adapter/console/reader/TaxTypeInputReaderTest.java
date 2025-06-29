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

class TaxTypeInputReaderTest {

	private TaxTypeReader taxTypeReader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		taxTypeReader = new TaxTypeInputReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(taxTypeReader);
	}

	@Test
	void shouldReturnTaxType_whenInputIsNoneTax() throws Exception {
		String input = "비과세";
		BufferedReader bufferedReader = new BufferedReader(new StringReader(input));

		String taxType = this.taxTypeReader.read(bufferedReader);

		assertEquals("비과세", taxType);
	}
}

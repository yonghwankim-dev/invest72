package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;

class ConsoleTaxTypeReaderTest {

	private TaxTypeReader taxTypeReader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		taxTypeReader = new ConsoleTaxTypeReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(taxTypeReader);
	}
}

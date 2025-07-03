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

class ConsoleInterestTypeReaderTest {

	private InterestTypeReader interestTypeReader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		interestTypeReader = new ConsoleInterestTypeReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(interestTypeReader);
	}

	@Test
	void shouldReturnInterestType_whenTextIsSimple() throws Exception {
		BufferedReader reader = new BufferedReader(new StringReader("단리"));

		String interestType = interestTypeReader.read(reader);

		assertEquals("단리", interestType);
	}

	@Test
	void shouldReturnInterestType_whenTextIsCompound() throws Exception {
		BufferedReader reader = new BufferedReader(new StringReader("복리"));

		String interestType = interestTypeReader.read(reader);

		assertEquals("복리", interestType);
	}
}

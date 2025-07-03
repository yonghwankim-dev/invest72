package application.reader.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.reader.InvestmentTypeReader;

class InvestmentTypeInputReaderTest {

	private InvestmentTypeReader delegator;
	private BufferedReader reader;

	private BufferedReader createBufferedReader(String text) {
		InputStream in = new ByteArrayInputStream(text.getBytes(StandardCharsets.UTF_8));
		return new BufferedReader(new InputStreamReader(in));
	}

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		GuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter);
		delegator = new InvestmentTypeInputReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(delegator);
	}

	@Test
	void shouldReturnFixedDeposit_whenTextIsFixedDeposit() throws IOException {
		reader = createBufferedReader("예금");

		String type = delegator.read(reader);

		assertEquals("예금", type);
	}

	@Test
	void shouldReturnInstallmentSaving_whenTextIsInstallmentSaving() throws IOException {
		reader = createBufferedReader("적금");

		String type = delegator.read(reader);

		assertEquals("적금", type);
	}
}

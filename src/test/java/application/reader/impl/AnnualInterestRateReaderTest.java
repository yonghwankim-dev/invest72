package application.reader.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.ui.BufferedWriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.reader.InterestRatePercentReader;

class AnnualInterestRateReaderTest {

	private InterestRatePercentReader reader;

	@BeforeEach
	void setUp() {
		PrintStream out = System.out;
		OutputStreamWriter outputStreamWriter = new OutputStreamWriter(out);
		BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
		PrintStream err = System.err;
		GuidePrinter guidePrinter = new BufferedWriterBasedGuidePrinter(bufferedWriter, err);
		reader = new AnnualInterestRateReader(guidePrinter);
	}

	@Test
	void created() {
		assertNotNull(reader);
	}

	@Test
	void shouldReturnInterestRatePercent_whenInputIsNumber() throws Exception {
		BufferedReader bufferedReader = new BufferedReader(new StringReader("5"));

		double annualInterestRate = this.reader.readInterestRate(bufferedReader);

		assertEquals(0.05, annualInterestRate);
	}
}

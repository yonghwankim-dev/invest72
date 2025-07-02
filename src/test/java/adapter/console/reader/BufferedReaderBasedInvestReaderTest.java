package adapter.console.reader;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;

class BufferedReaderBasedInvestReaderTest {
	@Test
	void created() {
		PrintStream printStream = System.out;
		OutputStreamWriter writer = new OutputStreamWriter(printStream);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		InvestReader investReader = new BufferedReaderBasedInvestReader(guidePrinter, reader);

		Assertions.assertNotNull(investReader);
	}

	@Test
	void readInvestmentType() throws Exception {
		PrintStream printStream = System.out;
		OutputStreamWriter writer = new OutputStreamWriter(printStream);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
		BufferedReader reader = new BufferedReader(new StringReader("예금"));
		InvestReader investReader = new BufferedReaderBasedInvestReader(guidePrinter, reader);

		String investmentType = investReader.readInvestmentType();

		Assertions.assertEquals("예금", investmentType);
	}
}

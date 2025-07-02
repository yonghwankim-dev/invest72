package adapter.console.reader;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import adapter.console.writer.GuidePrinter;
import adapter.console.writer.WriterBasedGuidePrinter;

class BufferedReaderBasedInvestReaderTest {

	private ByteArrayOutputStream outputStream;
	private GuidePrinter guidePrinter;

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
	}

	@Test
	void readInvestmentType() throws Exception {
		BufferedReader reader = new BufferedReader(new StringReader("예금"));
		InvestReader investReader = new BufferedReaderBasedInvestReader(guidePrinter, reader);

		String investmentType = investReader.readInvestmentType();

		String printed = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(printed.contains("투자 유형을 입력하세요 (예금 or 적금): "));
		assertEquals("예금", investmentType);
	}

	@Test
	void readInvestmentAmount_whenInvestmentTypeIsFixedDeposit() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("1000000"));
		InvestReader investReader = new BufferedReaderBasedInvestReader(guidePrinter, reader);

		String amount = investReader.readInvestmentAmount();

		String printed = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(printed.contains("예치 금액(원)을 입력하세요: "));
		assertEquals("1000000", amount);
	}
}

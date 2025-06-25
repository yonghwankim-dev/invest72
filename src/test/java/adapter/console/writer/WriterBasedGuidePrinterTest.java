package adapter.console.writer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WriterBasedGuidePrinterTest {

	private OutputStream outputStream;
	private GuidePrinter guidePrinter;

	@BeforeEach
	void setUp() {
		outputStream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);
	}

	@Test
	void created() {
		assertNotNull(guidePrinter);
	}

	@Test
	void shouldPrintFixedDepositAmountInputGuide() {
		guidePrinter.printFixedDepositAmountInputGuide();

		String output = outputStream.toString();
		assertTrue(output.contains("예치 금액(원)을 입력하세요"));
	}
}

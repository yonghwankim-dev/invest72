package adapter.console.writer;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.junit.jupiter.api.Test;

class WriterBasedGuidePrinterTest {

	@Test
	void created() {
		OutputStream outputStream = System.out;
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);

		assertNotNull(guidePrinter);
	}

	@Test
	void shouldPrintFixedDepositAmountInputGuide() {
		OutputStream outputStream = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(outputStream);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		GuidePrinter guidePrinter = new WriterBasedGuidePrinter(bufferedWriter);

		guidePrinter.printFixedDepositAmountInputGuide();

		String output = outputStream.toString();
		assertTrue(output.contains("예치 금액(원)을 입력하세요"));
	}
}

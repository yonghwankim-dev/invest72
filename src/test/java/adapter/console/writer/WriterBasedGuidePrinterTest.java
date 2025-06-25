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

	@Test
	void shouldPrintInstallmentInvestmentInputGuide() {
		guidePrinter.printInstallmentInvestmentInputGuide();

		String output = outputStream.toString();
		assertEquals(getInstallmentInvestmentInputGuide(), output);
	}

	private String getInstallmentInvestmentInputGuide() {
		return "\uD83D\uDCB0 투자 기간 단위와 금액을 한 줄로 입력해주세요.\n"
			+ "\n"
			+ "\uD83D\uDCDD 형식:\n"
			+ "[단위] [투자금액]\n"
			+ "\n"
			+ "\uD83D\uDCCC 단위 예시:\n"
			+ "- \"월\" → 적금 (매월 납입 금액)\n"
			+ "- \"년\" → 적금 (매년 납입 금액)\n"
			+ "\n"
			+ "\uD83D\uDCCC 예시 입력:\n"
			+ "- 월 1000000\n"
			+ "- 년 5000000\n"
			+ "\n"
			+ "\uD83D\uDC49 입력: \n";
	}
}

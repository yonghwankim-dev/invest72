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

import adapter.console.ui.WriterBasedGuidePrinter;
import adapter.ui.GuidePrinter;
import application.reader.InvestReader;

class BufferedReaderBasedInvestReaderTest {

	private ByteArrayOutputStream outputStream;
	private GuidePrinter guidePrinter;

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
		InvestReader investReader = new BufferedReaderBasedInvestReader(reader, guidePrinter);

		String investmentType = investReader.readInvestmentType();

		String printed = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(printed.contains("투자 유형을 입력하세요 (예금 or 적금): "));
		assertEquals("예금", investmentType);
	}

	@Test
	void readFixedDepositAmount_whenInvestmentTypeIsFixedDeposit() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("1000000"));
		InvestReader investReader = new BufferedReaderBasedInvestReader(reader, guidePrinter);

		String amount = investReader.readFixedDepositAmount();

		String printed = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(printed.contains("예치 금액(원)을 입력하세요: "));
		assertEquals("1000000", amount);
	}

	@Test
	void readInstallmentSavingAmount_whenInvestmentTypeIsInstallmentSaving() throws IOException {
		BufferedReader reader = new BufferedReader(new StringReader("월 1000000"));
		InvestReader investReader = new BufferedReaderBasedInvestReader(reader, guidePrinter);

		String amount = investReader.readInstallmentSavingAmount();

		String printed = outputStream.toString(StandardCharsets.UTF_8);
		assertTrue(printed.contains(getInstallmentInvestmentInputGuide()));
		assertEquals("월 1000000", amount);
	}
}

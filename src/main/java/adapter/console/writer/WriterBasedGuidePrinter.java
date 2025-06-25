package adapter.console.writer;

import java.io.BufferedWriter;
import java.io.IOException;

public class WriterBasedGuidePrinter implements GuidePrinter {

	private final BufferedWriter writer;

	public WriterBasedGuidePrinter(BufferedWriter writer) {
		this.writer = writer;
	}

	@Override
	public void printFixedDepositAmountInputGuide() {
		try {
			writer.write("예치 금액(원)을 입력하세요: ");
		} catch (IOException e) {
			System.err.println("Failed to write guide message: " + e.getMessage());
		} finally {
			try {
				writer.flush();
			} catch (IOException e) {
				System.err.println("Failed to flush writer: " + e.getMessage());
			}
		}
	}

	@Override
	public void printInstallmentInvestmentInputGuide() {
		String text = getInstallmentInvestmentInputGuide();
		try {
			writer.write(text);
		} catch (IOException e) {
			System.err.println("Failed to write guide message: " + e.getMessage());
		} finally {
			try {
				writer.flush();
			} catch (IOException e) {
				System.err.println("Failed to flush writer: " + e.getMessage());
			}
		}
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

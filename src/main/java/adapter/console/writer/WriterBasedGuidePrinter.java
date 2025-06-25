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
}

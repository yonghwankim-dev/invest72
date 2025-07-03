package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class BufferedReaderBasedInvestReader implements InvestReader {

	private final BufferedReader reader;
	private final GuidePrinter guidePrinter;

	public BufferedReaderBasedInvestReader(BufferedReader reader, GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
		this.reader = reader;
	}

	@Override
	public String readInvestmentType() throws IOException {
		guidePrinter.printInvestmentTypeInputGuide();
		return readLine();
	}

	private String readLine() throws IOException {
		return reader.readLine();
	}

	@Override
	public String readFixedDepositAmount() throws IOException {
		guidePrinter.printFixedDepositAmountInputGuide();
		return readLine();
	}

	@Override
	public String readInstallmentSavingAmount() throws IOException {
		guidePrinter.printInstallmentInvestmentInputGuide();
		return readLine();
	}

	@Override
	public String readPeriodType() throws IOException {
		guidePrinter.printPeriodTypeInputGuide();
		return readLine();
	}

	@Override
	public int readPeriodValue() throws IOException {
		guidePrinter.printPeriodInputGuide();
		return toInt(readLine());
	}

	@Override
	public String readInterestType() throws IOException {
		guidePrinter.printInterestTypeInputGuide();
		return readLine();
	}

	@Override
	public double readAnnualInterestRate() throws IOException {
		guidePrinter.printInterestRatePercentInputGuide();
		int percent = toInt(readLine());
		return toRate(percent);
	}

	private int toInt(String line) {
		try {
			return Integer.parseInt(line);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("올바른 숫자를 입력해주세요: " + line, e);
		}
	}

	private double toRate(double value) {
		return value / 100.0;
	}

	@Override
	public String readTaxType() throws IOException {
		guidePrinter.printTaxTypeInputGuide();
		return readLine();
	}

	@Override
	public double readTaxRate() throws IOException {
		guidePrinter.printTaxRateInputGuide();
		return toRate(Double.parseDouble(reader.readLine()));
	}
}

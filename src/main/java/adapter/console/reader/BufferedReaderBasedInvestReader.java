package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;

public class BufferedReaderBasedInvestReader implements InvestReader {

	private final GuidePrinter guidePrinter;
	private final BufferedReader reader;

	public BufferedReaderBasedInvestReader(GuidePrinter guidePrinter, BufferedReader reader) {
		this.guidePrinter = guidePrinter;
		this.reader = reader;
	}

	@Override
	public String readInvestmentType() throws IOException {
		guidePrinter.printInvestmentTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public String readInvestmentAmount() throws IOException {
		// todo: fix
		guidePrinter.printInstallmentInvestmentInputGuide();
		return reader.readLine();
	}

	@Override
	public String readPeriodType() throws IOException {
		guidePrinter.printPeriodTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public int readPeriodValue() throws IOException {
		guidePrinter.printPeriodInputGuide();
		return Integer.parseInt(reader.readLine());
	}

	@Override
	public String readInterestType() throws IOException {
		guidePrinter.printInterestTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public double readAnnualInterestRate() throws IOException {
		guidePrinter.printInterestRatePercentInputGuide();
		int percent = Integer.parseInt(reader.readLine());
		return toRate(percent);
	}

	private double toRate(double v) {
		return v / 100.0;
	}

	@Override
	public String readTaxType() throws IOException {
		guidePrinter.printTaxTypeInputGuide();
		return reader.readLine();
	}

	@Override
	public double readTaxRate() throws IOException {
		guidePrinter.printTaxRateInputGuide();
		return toRate(Double.parseDouble(reader.readLine()));
	}
}

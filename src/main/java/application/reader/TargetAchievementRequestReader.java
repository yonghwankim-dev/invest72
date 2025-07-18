package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;

public class TargetAchievementRequestReader
	implements TargetAmountReader, InvestmentAmountReader, InterestRateReader, TaxTypeReader, TaxRateReader {

	private final BufferedReader reader;
	private final GuidePrinter guidePrinter;

	public TargetAchievementRequestReader(BufferedReader reader, GuidePrinter guidePrinter) {
		this.reader = reader;
		this.guidePrinter = guidePrinter;
	}

	@Override
	public int readTargetAmount() throws IOException {
		guidePrinter.printTargetAmountInputGuide();
		return Integer.parseInt(reader.readLine());
	}

	@Override
	public String readAmount() throws IOException {
		guidePrinter.printMonthlyInvestmentInputGuide();
		return reader.readLine();
	}

	@Override
	public double readInterestRate() throws IOException {
		guidePrinter.printInterestPercentInputGuide();
		return toRate(Double.parseDouble(reader.readLine()));
	}

	private double toRate(double value) {
		return value / 100.0;
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

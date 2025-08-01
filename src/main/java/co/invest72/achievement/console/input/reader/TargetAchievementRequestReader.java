package co.invest72.achievement.console.input.reader;

import java.io.BufferedReader;
import java.io.IOException;

import co.invest72.investment.console.output.guide.GuidePrinter;

public class TargetAchievementRequestReader {

	private final BufferedReader reader;
	private final GuidePrinter guidePrinter;

	public TargetAchievementRequestReader(BufferedReader reader, GuidePrinter guidePrinter) {
		this.reader = reader;
		this.guidePrinter = guidePrinter;
	}

	public int readTargetAmount() throws IOException {
		guidePrinter.printTargetAmountInputGuide();
		return Integer.parseInt(reader.readLine());
	}

	public String readAmount() throws IOException {
		guidePrinter.printMonthlyInvestmentInputGuide();
		return reader.readLine();
	}

	public double readInterestRate() throws IOException {
		guidePrinter.printInterestPercentInputGuide();
		return toRate(Double.parseDouble(reader.readLine()));
	}

	private double toRate(double value) {
		return value / 100.0;
	}

	public String readTaxType() throws IOException {
		guidePrinter.printTaxTypeInputGuide();
		return reader.readLine();
	}

	public double readTaxRate() throws IOException {
		guidePrinter.printTaxRateInputGuide();
		return toRate(Double.parseDouble(reader.readLine()));
	}
}

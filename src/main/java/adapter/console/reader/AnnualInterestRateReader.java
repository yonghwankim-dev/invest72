package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.console.writer.GuidePrinter;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;

public class AnnualInterestRateReader implements InterestRatePercentReader {

	private final GuidePrinter guidePrinter;

	public AnnualInterestRateReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public InterestRate read(BufferedReader reader) throws IOException {
		guidePrinter.printInterestRatePercentInputGuide();
		int percent = Integer.parseInt(reader.readLine());
		return new AnnualInterestRate(toRate(percent));
	}

	private double toRate(int value) {
		return value / 100.0;
	}
}

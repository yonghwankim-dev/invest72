package application.reader.impl;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;
import application.reader.InterestRateReader;

public class AnnualInterestRateReader implements InterestRateReader {

	private final GuidePrinter guidePrinter;

	public AnnualInterestRateReader(GuidePrinter guidePrinter) {
		this.guidePrinter = guidePrinter;
	}

	@Override
	public double readInterestRate(BufferedReader reader) throws IOException {
		guidePrinter.printInterestRatePercentInputGuide();
		int percent = Integer.parseInt(reader.readLine());
		return toRate(percent);
	}

	private double toRate(int value) {
		return value / 100.0;
	}
}

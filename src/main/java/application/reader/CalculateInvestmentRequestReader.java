package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;

public class CalculateInvestmentRequestReader
	implements InvestmentTypeReader, PeriodTypeReader, PeriodReader, InterestTypeReader, InterestRateReader,
	TaxTypeReader, TaxRateReader {

	private final BufferedReader reader;
	private final GuidePrinter guidePrinter;

	public CalculateInvestmentRequestReader(BufferedReader reader, GuidePrinter guidePrinter) {
		this.reader = reader;
		this.guidePrinter = guidePrinter;
	}

	@Override
	public String readInvestmentType() throws IOException {
		return reader.readLine();
	}

	@Override
	public String readPeriodType() throws IOException {
		return reader.readLine();
	}

	@Override
	public int readPeriod() throws IOException {
		return Integer.parseInt(reader.readLine());
	}

	@Override
	public String readInterestType() throws IOException {
		return reader.readLine();
	}

	@Override
	public double readInterestRate() throws IOException {
		int percent = Integer.parseInt(reader.readLine());
		return toRate(percent);
	}

	private double toRate(double value) {
		return value / 100.0;
	}

	@Override
	public String readTaxType() throws IOException {
		return reader.readLine();
	}

	@Override
	public double readTaxRate() throws IOException {
		return toRate(Double.parseDouble(reader.readLine()));
	}
}

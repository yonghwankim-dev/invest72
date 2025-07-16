package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

// implements InterestRateReader,
// TaxTypeReader, TaxRateReader
public class CalculateInvestmentRequestReader
	implements InvestmentTypeReader, PeriodTypeReader, PeriodReader, InterestTypeReader, InterestRateReader {

	private final BufferedReader reader;

	public CalculateInvestmentRequestReader(BufferedReader reader) {
		this.reader = reader;
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
	public double readInterestRate(BufferedReader reader) throws IOException {
		int percent = Integer.parseInt(reader.readLine());
		return toRate(percent);
	}

	private double toRate(int value) {
		return value / 100.0;
	}
}

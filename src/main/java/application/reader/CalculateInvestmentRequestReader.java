package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

// implements InterestTypeReader, InterestRatePercentReader,
// TaxTypeReader, TaxRateReader
public class CalculateInvestmentRequestReader
	implements InvestmentTypeReader, PeriodTypeReader, PeriodReader, InterestTypeReader {

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
}

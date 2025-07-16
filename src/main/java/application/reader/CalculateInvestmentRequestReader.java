package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

// implements InvestmentTypeReader, PeriodTypeReader, PeriodReader, InterestTypeReader, InterestRatePercentReader,
// TaxTypeReader, TaxRateReader
public class CalculateInvestmentRequestReader implements InvestmentTypeReader, PeriodTypeReader {

	@Override
	public String readInvestmentType(BufferedReader reader) throws IOException {
		return reader.readLine();
	}

	@Override
	public String readPeriodType(BufferedReader reader) throws IOException {
		return reader.readLine();
	}
}

package adapter.console.reader;

import java.io.IOException;

public interface InvestReader {
	String readInvestmentType() throws IOException;

	String readInvestmentAmount(String investmentType) throws IOException;

	String readPeriodType() throws IOException;

	int readPeriodValue() throws IOException;

	String readInterestType() throws IOException;

	double readAnnualInterestRate() throws IOException;

	String readTaxType() throws IOException;

	double readTaxRate() throws IOException;
}

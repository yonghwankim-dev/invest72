package application.reader;

import java.io.IOException;

public interface InvestReader {
	String readInvestmentType() throws IOException;

	String readFixedDepositAmount() throws IOException;

	String readInstallmentSavingAmount() throws IOException;

	String readPeriodType() throws IOException;

	int readPeriodValue() throws IOException;

	String readInterestType() throws IOException;

	double readAnnualInterestRate() throws IOException;

	String readTaxType() throws IOException;

	double readTaxRate() throws IOException;
}

package application.reader;

import java.io.IOException;

public interface InvestReader {
	String readInvestmentType() throws IOException;

	String readFixedDepositAmount() throws IOException;

	String readInstallmentSavingAmount() throws IOException;
}

package adapter.console.reader;

import java.io.IOException;

public class FixedDepositAmountReaderStrategy implements InvestmentAmountReaderStrategy {
	
	@Override
	public String readAmount(InvestReader reader) throws IOException {
		return reader.readFixedDepositAmount();
	}
}

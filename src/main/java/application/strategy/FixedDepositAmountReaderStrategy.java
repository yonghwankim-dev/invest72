package application.strategy;

import java.io.IOException;

import application.reader.InvestReader;

public class FixedDepositAmountReaderStrategy implements InvestmentAmountReaderStrategy {

	@Override
	public String readAmount(InvestReader reader) throws IOException {
		return reader.readFixedDepositAmount();
	}
}

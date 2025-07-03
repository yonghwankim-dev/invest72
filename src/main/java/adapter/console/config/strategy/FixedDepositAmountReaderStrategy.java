package adapter.console.config.strategy;

import java.io.IOException;

import application.reader.InvestReader;
import application.strategy.InvestmentAmountReaderStrategy;

public class FixedDepositAmountReaderStrategy implements InvestmentAmountReaderStrategy {

	@Override
	public String readAmount(InvestReader reader) throws IOException {
		return reader.readFixedDepositAmount();
	}
}

package application.strategy;

import java.io.IOException;

import application.reader.InvestReader;
import application.reader.InvestmentAmountReader;

public class FixedDepositAmountReaderStrategy implements InvestmentAmountReaderStrategy {

	@Override
	public String readAmount(InvestReader reader) throws IOException {
		return reader.readFixedDepositAmount();
	}

	@Override
	public String readAmount(InvestmentAmountReader reader) throws IOException {
		return reader.readAmount();
	}
}

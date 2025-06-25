package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.invest_amount.InvestmentAmount;

public class FixedDepositAmountReader implements InvestmentAmountReader {
	@Override
	public InvestmentAmount read(BufferedReader reader) throws IOException {
		return null;
	}
}

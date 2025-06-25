package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.invest_amount.InvestmentAmount;

public interface InvestmentAmountReader {
	InvestmentAmount read(BufferedReader reader) throws IOException;
}

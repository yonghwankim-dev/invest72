package adapter.console;

import java.io.BufferedReader;
import java.io.IOException;

import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

public interface InvestmentAmountReader {
	InvestmentAmount read(InvestmentType investmentType, BufferedReader reader) throws IOException;
}

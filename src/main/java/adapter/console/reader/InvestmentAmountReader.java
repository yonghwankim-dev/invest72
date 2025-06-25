package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

public interface InvestmentAmountReader {
	InvestmentAmount read(BufferedReader reader) throws IOException;

	boolean supports(InvestmentType investmentType);
}

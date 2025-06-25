package adapter.console.reader;

import java.io.BufferedReader;
import java.io.IOException;

import domain.invest_amount.InvestmentAmount;
import domain.type.InvestmentType;

public interface InvestmentAmountReaderDelegator {
	InvestmentAmount read(InvestmentType investmentType, BufferedReader reader) throws IOException;
}

package application.delegator;

import java.io.BufferedReader;
import java.io.IOException;

import co.invest72.investment.domain.InvestmentAmount;
import co.invest72.investment.domain.investment.InvestmentType;

public interface InvestmentAmountReaderDelegator {
	InvestmentAmount read(InvestmentType investmentType, BufferedReader reader) throws IOException;

	String read(BufferedReader reader) throws IOException;
}

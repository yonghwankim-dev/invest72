package application.delegator;

import java.io.BufferedReader;
import java.io.IOException;

import domain.amount.InvestmentAmount;
import domain.type.InvestmentType;

public interface InvestmentAmountReaderDelegator {
	InvestmentAmount read(InvestmentType investmentType, BufferedReader reader) throws IOException;

	String read(BufferedReader reader) throws IOException;
}

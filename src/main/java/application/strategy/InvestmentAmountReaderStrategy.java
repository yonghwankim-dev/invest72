package application.strategy;

import java.io.IOException;

import application.reader.InvestReader;

public interface InvestmentAmountReaderStrategy {
	String readAmount(InvestReader reader) throws IOException;
}

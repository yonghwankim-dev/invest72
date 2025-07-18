package application.strategy;

import java.io.IOException;

import application.reader.InvestmentAmountReader;

public interface InvestmentAmountReaderStrategy {
	String readAmount(InvestmentAmountReader reader) throws IOException;
}

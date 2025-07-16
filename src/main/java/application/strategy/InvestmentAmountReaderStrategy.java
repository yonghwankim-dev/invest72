package application.strategy;

import java.io.IOException;

import application.reader.InvestReader;
import application.reader.InvestmentAmountReader;

public interface InvestmentAmountReaderStrategy {
	String readAmount(InvestReader reader) throws IOException;

	String readAmount(InvestmentAmountReader reader) throws IOException;
}

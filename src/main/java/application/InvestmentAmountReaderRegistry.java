package application;

import java.util.List;

import adapter.console.reader.InvestmentAmountReader;
import domain.type.InvestmentType;

public interface InvestmentAmountReaderRegistry {
	List<InvestmentAmountReader> getReaders();

	InvestmentAmountReader getReaderBy(InvestmentType investmentType);
}

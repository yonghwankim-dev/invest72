package application.registry;

import java.util.List;

import application.reader.InvestmentAmountReader;
import domain.type.InvestmentType;

public interface InvestmentAmountReaderRegistry {
	List<InvestmentAmountReader> getReaders();

	InvestmentAmountReader getReaderBy(InvestmentType investmentType);
}

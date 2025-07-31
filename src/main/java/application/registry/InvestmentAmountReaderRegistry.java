package application.registry;

import java.util.List;

import application.reader.InvestmentAmountReader;
import co.invest72.investment.domain.investment.InvestmentType;

public interface InvestmentAmountReaderRegistry {
	List<InvestmentAmountReader> getReaders();

	InvestmentAmountReader getReaderBy(InvestmentType investmentType);
}

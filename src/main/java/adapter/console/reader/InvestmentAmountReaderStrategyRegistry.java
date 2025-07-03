package adapter.console.reader;

import domain.type.InvestmentType;

public interface InvestmentAmountReaderStrategyRegistry {
	InvestmentAmountReaderStrategy getStrategy(InvestmentType investmentType) throws IllegalArgumentException;
}

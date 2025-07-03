package application.registry;

import application.strategy.InvestmentAmountReaderStrategy;
import domain.type.InvestmentType;

public interface InvestmentAmountReaderStrategyRegistry {
	InvestmentAmountReaderStrategy getStrategy(InvestmentType investmentType) throws IllegalArgumentException;
}

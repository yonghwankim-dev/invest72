package application.registry;

import application.strategy.InvestmentAmountReaderStrategy;
import co.invest72.investment.domain.investment.InvestmentType;

public interface InvestmentAmountReaderStrategyRegistry {
	InvestmentAmountReaderStrategy getStrategy(InvestmentType investmentType) throws IllegalArgumentException;
}

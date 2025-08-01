package co.invest72.investment.console.input.registry;

import co.invest72.investment.console.input.strategy.InvestmentAmountReaderStrategy;
import co.invest72.investment.domain.investment.InvestmentType;

public interface InvestmentAmountReaderStrategyRegistry {
	InvestmentAmountReaderStrategy getStrategy(InvestmentType type) throws IllegalArgumentException;
}

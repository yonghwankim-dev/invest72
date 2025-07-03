package adapter.console.config.registry;

import java.util.Map;

import application.registry.InvestmentAmountReaderStrategyRegistry;
import application.strategy.InvestmentAmountReaderStrategy;
import domain.type.InvestmentType;

public class MapBasedInvestmentAmountReaderStrategyRegistry implements InvestmentAmountReaderStrategyRegistry {

	private final Map<InvestmentType, InvestmentAmountReaderStrategy> strategies;

	public MapBasedInvestmentAmountReaderStrategyRegistry(
		Map<InvestmentType, InvestmentAmountReaderStrategy> strategies) {
		this.strategies = strategies;
	}

	@Override
	public InvestmentAmountReaderStrategy getStrategy(InvestmentType investmentType) throws IllegalArgumentException {
		if (strategies.containsKey(investmentType)) {
			return strategies.get(investmentType);
		}
		throw new IllegalArgumentException("지원하지 않는 투자 유형입니다: " + investmentType);
	}
}

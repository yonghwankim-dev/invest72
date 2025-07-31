package application.registry;

import java.util.Map;

import application.strategy.InvestmentAmountReaderStrategy;
import co.invest72.investment.domain.investment.InvestmentType;

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

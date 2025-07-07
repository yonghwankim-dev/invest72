package application.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import application.key.InvestmentKey;
import application.request.CalculateInvestmentRequest;
import domain.investment.MonthlyInvestment;
import domain.type.InterestType;
import domain.type.InvestmentType;

public class MonthlyInvestmentFactory implements InvestmentFactory<MonthlyInvestment> {
	private final Map<InvestmentKey, Function<CalculateInvestmentRequest, MonthlyInvestment>> registry = new HashMap<>();

	@Override
	public MonthlyInvestment createBy(CalculateInvestmentRequest request) {
		InvestmentKey key = createInvestmentKey(request.type(), request.interestType());
		Function<CalculateInvestmentRequest, MonthlyInvestment> creator = registry.get(key);
		if (creator == null) {
			throw new IllegalArgumentException("Unsupported investment type or interest type: " + key);
		}
		return creator.apply(request);
	}

	private InvestmentKey createInvestmentKey(String investmentTypeValue, String interestTypeValue) {
		InvestmentType type = InvestmentType.from(investmentTypeValue);
		InterestType interestType = InterestType.from(interestTypeValue);
		return new InvestmentKey(type, interestType);
	}
}

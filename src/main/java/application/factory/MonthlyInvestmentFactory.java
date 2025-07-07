package application.factory;

import application.request.CalculateInvestmentRequest;
import domain.investment.MonthlyInvestment;

public class MonthlyInvestmentFactory implements InvestmentFactory<MonthlyInvestment> {

	@Override
	public MonthlyInvestment createBy(CalculateInvestmentRequest request) {
		return null;
	}
}

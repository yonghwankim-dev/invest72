package co.invest72.investment.application;

import java.util.ArrayList;
import java.util.List;

import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateMonthlyInvestmentResponse;
import application.response.MonthlyInvestmentResult;
import co.invest72.investment.domain.Investment;

public class CalculateMonthlyInvestment {
	private final InvestmentFactory<Investment> investmentFactory;

	public CalculateMonthlyInvestment(InvestmentFactory<Investment> investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	public CalculateMonthlyInvestmentResponse calMonthlyInvestmentAmount(CalculateInvestmentRequest request) {
		List<MonthlyInvestmentResult> result = new ArrayList<>();
		Investment investment = investmentFactory.createBy(request);

		for (int month = 1; month <= investment.getFinalMonth(); month++) {
			result.add(new MonthlyInvestmentResult(
				month,
				investment.getPrincipal(month),
				investment.getInterest(month),
				investment.getTax(month),
				investment.getTotalProfit(month)
			));
		}
		return new CalculateMonthlyInvestmentResponse(result);
	}
}

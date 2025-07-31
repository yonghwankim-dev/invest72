package co.invest72.investment.application;

import java.util.ArrayList;
import java.util.List;

import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import application.response.CalculateMonthlyInvestmentResponse;
import application.response.MonthlyInvestmentResult;
import co.invest72.investment.domain.Investment;

public class CalculateInvestment implements InvestmentUseCase {

	private final InvestmentFactory<Investment> investmentFactory;

	public CalculateInvestment(InvestmentFactory<Investment> investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	@Override
	public CalculateInvestmentResponse calInvestmentAmount(CalculateInvestmentRequest request) {
		Investment investment = investmentFactory.createBy(request);
		int totalProfitAmount = investment.getTotalProfit();
		int totalPrincipalAmount = investment.getPrincipal();
		int interest = investment.getInterest();
		int tax = investment.getTax();
		return new CalculateInvestmentResponse(totalProfitAmount, totalPrincipalAmount, interest, tax);
	}

	@Override
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

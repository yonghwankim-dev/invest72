package application.usecase;

import java.util.ArrayList;
import java.util.List;

import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import application.response.CalculateMonthlyInvestmentResponse;
import application.response.MonthlyInvestmentResult;
import domain.investment.Investment;
import domain.investment.MonthlyInvestment;

public class CalculateInvestmentUseCase implements InvestmentUseCase {

	private final InvestmentFactory<Investment> investmentFactory;
	private final InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory;

	public CalculateInvestmentUseCase(InvestmentFactory<Investment> investmentFactory,
		InvestmentFactory<MonthlyInvestment> monthlyInvestmentFactory) {
		this.investmentFactory = investmentFactory;
		this.monthlyInvestmentFactory = monthlyInvestmentFactory;
	}

	@Override
	public CalculateInvestmentResponse calInvestmentAmount(CalculateInvestmentRequest request) {
		Investment investment = investmentFactory.createBy(request);
		int totalProfitAmount = investment.getAmount();
		int totalPrincipalAmount = investment.getPrincipalAmount();
		int interest = investment.getInterest();
		int tax = investment.getTax();
		return new CalculateInvestmentResponse(totalProfitAmount, totalPrincipalAmount, interest, tax);
	}

	@Override
	public CalculateMonthlyInvestmentResponse calMonthlyInvestmentAmount(CalculateInvestmentRequest request) {
		List<MonthlyInvestmentResult> result = new ArrayList<>();
		MonthlyInvestment investment = monthlyInvestmentFactory.createBy(request);

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

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

	public CalculateInvestmentUseCase(InvestmentFactory<Investment> investmentFactory) {
		this.investmentFactory = investmentFactory;
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
		MonthlyInvestment investment = (MonthlyInvestment)investmentFactory.createBy(request);

		for (int i = 1; i <= investment.getFinalMonth(); i++) {
			result.add(new MonthlyInvestmentResult(
				i,
				investment.getAccumulatedPrincipal(i),
				investment.getAccumulatedInterest(i),
				investment.getAccumulatedTax(i),
				investment.getAccumulatedTotalProfit(i)
			));
		}
		return new CalculateMonthlyInvestmentResponse(result);
	}
}

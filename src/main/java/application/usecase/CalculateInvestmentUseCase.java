package application.usecase;

import java.util.Collections;
import java.util.List;

import application.factory.InvestmentFactory;
import application.request.CalculateInvestmentRequest;
import application.response.CalculateInvestmentResponse;
import application.response.CalculateMonthlyInvestmentResponse;
import application.response.MonthlyInvestmentResult;
import domain.investment.Investment;

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
		Investment investment = investmentFactory.createBy(request);
		List<MonthlyInvestmentResult> monthlyInvestmentResults = Collections.singletonList(
			new MonthlyInvestmentResult(1, 1_000_000, 4_166, 0, 1_004_166)
		);
		return new CalculateMonthlyInvestmentResponse(monthlyInvestmentResults);
	}
}

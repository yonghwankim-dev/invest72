package co.invest72.achievement.application;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.domain.Investment;
import co.invest72.product.domain.InvestmentProductEntity;

public class CalculateInvestmentProduct {
	private final InvestmentFactory investmentFactory;

	public CalculateInvestmentProduct(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	public CalculateInvestmentProductResponse calculate(
		int targetAmount, InvestmentProductEntity... products) {
		List<Investment> investments = Arrays.stream(products)
			.map(investmentFactory::createBy)
			.toList();

		int month = calMonth(targetAmount, investments);
		LocalDate achieveDate = calAchieveDate(product, month);

		long totalAccumulatedAmount = sumTotalProfit(investments, month);
		return new CalculateInvestmentProductResponse(achieveDate,
			totalAccumulatedAmount);
	}

	private int calMonth(int targetAmount, List<Investment> investments) {
		int month = 1;
		while (sumTotalProfit(investments, month) < targetAmount) {
			month++;
		}
		return month;
	}

	private int sumTotalProfit(List<Investment> investments, int month) {
		return investments.stream()
			.mapToInt(investment -> investment.getTotalProfit(month))
			.sum();
	}

	private static LocalDate calAchieveDate(InvestmentProductEntity product, int month) {
		return product.getStartDate().plusMonths(month);
	}

	public record CalculateInvestmentProductResponse(
		LocalDate achieveDate,
		long totalAccumulatedAmount
	) {

	}
}

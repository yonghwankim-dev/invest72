package co.invest72.achievement.application;

import java.time.LocalDate;

import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.domain.Investment;
import co.invest72.product.domain.InvestmentProductEntity;

public class CalculateInvestmentProduct {
	private final InvestmentFactory investmentFactory;

	public CalculateInvestmentProduct(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	public CalculateInvestmentProductResponse calculate(
		InvestmentProductEntity product, int targetAmount) {
		Investment investment = investmentFactory.createBy(product);

		int month = calMonth(targetAmount, investment);
		LocalDate achieveDate = calAchieveDate(product, month);
		long totalAccumulatedAmount = investment.getTotalProfit(month);
		return new CalculateInvestmentProductResponse(achieveDate,
			totalAccumulatedAmount);
	}

	private int calMonth(int targetAmount, Investment investment) {
		int month = 1;
		try {
			while (investment.getTotalProfit(month) < targetAmount) {
				month++;
			}
		} catch (IllegalArgumentException e) {
			// 투자 상품이 목표 금액에 도달할 수 없는 경우
			throw new IllegalArgumentException("투자 상품이 목표 금액에 도달할 수 없습니다.");
		}
		return month;
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

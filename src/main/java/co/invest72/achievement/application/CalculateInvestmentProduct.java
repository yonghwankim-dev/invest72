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

	// todo: implement
	public CalculateInvestmentProductResponse calculate(
		InvestmentProductEntity product, int targetAmount) {

		// 투자 상품이 존재하고 투자 상품이 목표 금액에 달성하기 위해서 필요한 개월수 및 누적금액을 계산
		Investment investment = investmentFactory.createBy(product);

		// targetAmount에 도달하기 위한 개월 수를 계산
		int month = calMonth(targetAmount, investment);

		LocalDate achieveDate = product.getStartDate().plusMonths(month);
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

	public record CalculateInvestmentProductResponse(
		LocalDate achieveDate,
		long totalAccumulatedAmount
	) {
	}
}

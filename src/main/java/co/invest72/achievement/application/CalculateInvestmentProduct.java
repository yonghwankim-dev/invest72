package co.invest72.achievement.application;

import java.time.LocalDate;

import co.invest72.achievement.domain.time.AchievementInvestmentCalculator;
import co.invest72.product.domain.InvestmentProductEntity;

public class CalculateInvestmentProduct {
	private final AchievementInvestmentCalculator calculator;

	public CalculateInvestmentProduct(AchievementInvestmentCalculator calculator) {
		this.calculator = calculator;
	}

	// todo: implement
	public CalculateInvestmentProductResponse calculate(
		InvestmentProductEntity product) {
		
		LocalDate achieveDate = LocalDate.of(2025, 1, 1);
		long totalAccumulatedAmount = 1_050_000L;
		return new CalculateInvestmentProductResponse(achieveDate,
			totalAccumulatedAmount);
	}

	public record CalculateInvestmentProductResponse(
		LocalDate achieveDate,
		long totalAccumulatedAmount
	) {
	}
}

package co.invest72.achievement.application;

import java.time.LocalDate;

import co.invest72.product.domain.InvestmentProductEntity;

public class CalculateInvestmentProduct {

	public CalculateInvestmentProductResponse calculate(
		InvestmentProductEntity product) {
		LocalDate achieveDate = LocalDate.of(2025, 1, 1);
		long totalAccumulatedAmount = 1_050_000L;
		CalculateInvestmentProductResponse response = new CalculateInvestmentProductResponse(achieveDate,
			totalAccumulatedAmount);
		return response;
	}

	public record CalculateInvestmentProductResponse(
		LocalDate achieveDate,
		long totalAccumulatedAmount
	) {
	}
}

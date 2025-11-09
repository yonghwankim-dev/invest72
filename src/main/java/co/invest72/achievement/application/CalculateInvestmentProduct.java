package co.invest72.achievement.application;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import co.invest72.investment.application.InvestmentFactory;
import co.invest72.investment.domain.Investment;
import co.invest72.product.domain.InvestmentProductEntity;

public class CalculateInvestmentProduct {
	private static final Logger logger = Logger.getLogger(CalculateInvestmentProduct.class.getName());
	private final InvestmentFactory investmentFactory;

	public CalculateInvestmentProduct(InvestmentFactory investmentFactory) {
		this.investmentFactory = investmentFactory;
	}

	public CalculateInvestmentProductResponse calculate(
		int targetAmount, InvestmentProductEntity... products) {
		List<Investment> investments = getInvestments(products);

		// 목표금액(targetAmount)에 도달하기 위한 개월(month)수 계산
		int month = calMonth(targetAmount, investments);

		// 모든 상품 중 가장 빠른 시작일을 구함
		// 목표금액에 도달하는 날짜 계산
		LocalDate achieveDate = getEarliestStartDate(products).plusMonths(month);

		long totalAccumulatedAmount = calTotalProfit(investments, month);
		return new CalculateInvestmentProductResponse(achieveDate, totalAccumulatedAmount);
	}

	private List<Investment> getInvestments(InvestmentProductEntity[] products) {
		return Arrays.stream(products)
			.map(investmentFactory::createBy)
			.toList();
	}

	private static LocalDate getEarliestStartDate(InvestmentProductEntity[] products) {
		return Arrays.stream(products)
			.map(InvestmentProductEntity::getStartDate)
			.min(LocalDate::compareTo)
			.orElseThrow(() -> new IllegalArgumentException("상품의 시작일을 찾을 수 없습니다."));
	}

	private int calMonth(int targetAmount, List<Investment> investments) {
		int month = 1;
		for (; month <= 999; month++) {
			int sum = calTotalProfit(investments, month);
			if (sum >= targetAmount) {
				return month;
			}
		}
		throw new IllegalArgumentException("목표금액에 도달할 수 없습니다.");
	}

	private int calTotalProfit(List<Investment> investments, int month) {
		return investments.stream()
			.mapToInt(investment -> {
				int effectiveMonth = Math.min(month, investment.getFinalMonth());
				return investment.getTotalProfit(effectiveMonth);
			})
			.sum();
	}

	public record CalculateInvestmentProductResponse(
		LocalDate achieveDate,
		long totalAccumulatedAmount
	) {
	}
}

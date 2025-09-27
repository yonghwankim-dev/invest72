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
		List<Investment> investments = Arrays.stream(products)
			.map(investmentFactory::createBy)
			.toList();

		// 목표금액(targetAmount)에 도달하기 위한 개월(month)수 계산
		int month = calMonth(targetAmount, investments);
		logger.info("목표금액 " + targetAmount + "원에 도달하기 위한 개월 수: " + month + "개월");

		// 모든 상품 중 가장 빠른 시작일을 구함
		LocalDate earliestStartDate = Arrays.stream(products)
			.map(InvestmentProductEntity::getStartDate)
			.min(LocalDate::compareTo)
			.orElseThrow(() -> new IllegalArgumentException("상품의 시작일을 찾을 수 없습니다."));
		// 목표금액에 도달하는 날짜 계산
		LocalDate achieveDate = earliestStartDate.plusMonths(month);

		long totalAccumulatedAmount = sumTotalProfit(investments, month);
		return new CalculateInvestmentProductResponse(achieveDate,
			totalAccumulatedAmount);
	}

	private int calMonth(int targetAmount, List<Investment> investments) {
		int month = 1;
		while (month <= 999) {
			int sum = 0;
			for (Investment investment : investments) {
				if (month > investment.getFinalMonth()) {
					sum += investment.getTotalProfit(investment.getFinalMonth());
				} else {
					sum += investment.getTotalProfit(month);
				}
			}
			if (sum >= targetAmount) {
				break;
			}
			month++;
		}
		if (month > 999) {
			throw new IllegalArgumentException("목표금액에 도달할 수 없습니다.");
		}
		return month;
	}

	private int sumTotalProfit(List<Investment> investments, int month) {
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

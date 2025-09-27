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
		// 목표금액에 도달하는 날짜 계산
		LocalDate achieveDate = products[0].getStartDate().plusMonths(month);

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

	public record CalculateInvestmentProductResponse(
		LocalDate achieveDate,
		long totalAccumulatedAmount
	) {

	}
}

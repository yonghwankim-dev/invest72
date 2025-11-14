package co.invest72.investment.domain.amount;

import java.math.BigDecimal;
import java.util.stream.IntStream;

import co.invest72.investment.domain.InterestRate;
import co.invest72.investment.domain.InvestmentAmount;

public class MonthlyAmount implements InvestmentAmount {

	private final int amount;

	public MonthlyAmount(int amount) {
		this.amount = amount;
		if (amount <= 0) {
			throw new IllegalArgumentException("Monthly amount must be positive.");
		}
	}

	/**
	 * 월 적립투자금액의 연간 이자 수익금액 계산하여 반환한다
	 * <p>
	 * 연간 이자 수익금액 = 월 이자 수익금액 * 적립 개월 수(12개월)
	 * </p>
	 * @param interestRate 이자율
	 * @return 연간 이자 수익금액
	 */
	@Override
	public double calAnnualInterest(InterestRate interestRate) {
		return IntStream.rangeClosed(1, 12)
			.mapToDouble(month -> calMonthlyInterest(interestRate)
				.multiply(BigDecimal.valueOf(month))
				.doubleValue()).sum();
	}

	@Override
	public BigDecimal calMonthlyInterest(InterestRate interestRate) {
		return interestRate.calMonthlyInterest(amount);
	}

	@Override
	public BigDecimal addAmount(BigDecimal amount) {
		return BigDecimal.valueOf(this.amount).add(amount);
	}
}

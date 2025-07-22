package domain.interest_rate;

import java.math.BigDecimal;
import java.util.Objects;

import domain.invest_period.InvestPeriod;
import util.BigDecimalUtils;

public class AnnualInterestRate implements InterestRate {

	private final double annualRate;

	public AnnualInterestRate(double annualRate) {
		this.annualRate = annualRate;
		if (this.annualRate < 0) {
			throw new IllegalArgumentException("Annual interest rate must be non-negative.");
		}
		if (this.annualRate >= 1.0) {
			throw new IllegalArgumentException("Annual interest rate must be less than 100%.");
		}
	}

	@Override
	public BigDecimal getAnnualRate() {
		return BigDecimal.valueOf(this.annualRate);
	}

	@Override
	public BigDecimal getMonthlyRate() {
		BigDecimal dividend = BigDecimal.valueOf(this.annualRate);
		BigDecimal divisor = BigDecimal.valueOf(12);
		return BigDecimalUtils.divide(dividend, divisor);
	}

	@Override
	public BigDecimal getAnnualInterest(int amount) {
		return BigDecimal.valueOf(amount * this.annualRate);
	}

	@Override
	public BigDecimal calTotalGrowthFactor(InvestPeriod investPeriod) {
		BigDecimal growthFactor = getGrowthFactor();
		int months = investPeriod.getMonths();
		return BigDecimalUtils.pow(growthFactor, months);
	}

	/**
	 * 월 이자율을 적용한 성장 계수를 반환합니다.
	 * 성장 계수 = 1 + 월 이자율
	 */
	private BigDecimal getGrowthFactor() {
		return BigDecimalUtils.round(getMonthlyRate().add(BigDecimal.ONE));
	}

	@Override
	public BigDecimal calGrowthFactor(int month) {
		return BigDecimalUtils.pow(getGrowthFactor(), month - 1);
	}

	@Override
	public BigDecimal calMonthlyInterest(int amount) {
		BigDecimal amountDecimal = BigDecimal.valueOf(amount);
		BigDecimal monthlyRate = getMonthlyRate();
		return amountDecimal.multiply(monthlyRate);
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		AnnualInterestRate that = (AnnualInterestRate)object;
		return Double.compare(annualRate, that.annualRate) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(annualRate);
	}
}

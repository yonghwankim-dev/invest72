package domain.interest_rate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import domain.invest_period.InvestPeriod;

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
	public double getAnnualRate() {
		return this.annualRate;
	}

	@Override
	public BigDecimal getMonthlyRate() {
		BigDecimal divisor = BigDecimal.valueOf(12);
		int scale = 6;
		return BigDecimal.valueOf(this.annualRate).divide(divisor, scale, RoundingMode.HALF_UP);
	}

	@Override
	public double getAnnualInterest(int amount) {
		return amount * this.annualRate;
	}

	@Override
	public double calTotalGrowthFactor(InvestPeriod investPeriod) {
		return Math.pow(getGrowthFactor(), investPeriod.getMonths());
	}

	/**
	 * 월 이자율을 적용한 성장 계수를 반환합니다.
	 * 성장 계수 = 1 + 월 이자율
	 */
	private double getGrowthFactor() {
		return 1 + getMonthlyRate().doubleValue();
	}

	@Override
	public double calGrowthFactor(int month) {
		return Math.pow(getGrowthFactor(), month - 1);
	}

	@Override
	public BigDecimal calMonthlyInterest(int amount) {
		return BigDecimal.valueOf(amount * getMonthlyRate().doubleValue());
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

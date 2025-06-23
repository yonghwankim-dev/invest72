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
	public double getMonthlyRate() {
		return this.annualRate / 12.0;
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
		return 1 + getMonthlyRate();
	}
}

public class AnnualInterestRate implements InterestRate {

	private final double annualRate;

	public AnnualInterestRate(double annualRate) {
		this.annualRate = annualRate;
		if (annualRate < 0) {
			throw new IllegalArgumentException("Annual interest rate must be non-negative.");
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
}

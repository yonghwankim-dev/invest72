public class AnnualInterestRate implements InterestRate {

	private final double annualRate;

	public AnnualInterestRate(double annualRate) {
		this.annualRate = annualRate;
	}

	@Override
	public double getAnnualRate() {
		return this.annualRate;
	}

	@Override
	public double getMonthlyRate() {
		return this.annualRate / 12.0;
	}
}

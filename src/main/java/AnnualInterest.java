public class AnnualInterest implements Interest {

	private final double annualRate;

	public AnnualInterest(double annualRate) {
		this.annualRate = annualRate;
	}

	@Override
	public double getAnnualRate() {
		return this.annualRate;
	}
}

public class MonthlyInvestPeriod implements InvestPeriod {
	private final int months;

	public MonthlyInvestPeriod(int months) {
		this.months = months;
	}

	@Override
	public int getMonths() {
		return months;
	}
}

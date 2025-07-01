package domain.invest_period;

public class MonthBasedRemainingPeriodProvider implements RemainingPeriodProvider {

	private final PeriodRange periodRange;

	public MonthBasedRemainingPeriodProvider(PeriodRange periodRange) {
		this.periodRange = periodRange;
	}

	@Override
	public double calRemainingPeriodInYears(int currentMonth) {
		int months = periodRange.toMonths();
		return (months - currentMonth) / 12.0;
	}
}

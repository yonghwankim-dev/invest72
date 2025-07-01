package domain.invest_period;

public class MonthBasedRemainingPeriodProvider implements RemainingPeriodProvider {

	@Override
	public double calRemainingPeriodInYears(int currentMonth) {
		int months = 12;
		return (months - currentMonth) / 12.0;
	}
}

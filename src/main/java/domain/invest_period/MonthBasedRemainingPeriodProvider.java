package domain.invest_period;

public class MonthBasedRemainingPeriodProvider implements RemainingPeriodProvider {

	@Override
	public double calRemainingPeriodInYears(int currentMonth) {
		return 1.0;
	}
}

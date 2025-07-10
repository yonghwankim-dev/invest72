package domain.invest_amount;

public class MonthlyInvestmentAmount implements TargetDurationCalculator {

	private final int amount;

	public MonthlyInvestmentAmount(int amount) {
		this.amount = amount;
		if (amount <= 0) {
			throw new IllegalArgumentException("월 투자 금액은 0보다 커야 합니다.");
		}
	}

	@Override
	public int calMonthsToReachTarget(int targetAmount) {
		if (amount >= targetAmount) {
			return 0;
		}
		return (targetAmount / amount) - 1;
	}
}

package domain.invest_amount;

public class MonthlyInvestmentAmount implements TargetAmountReachable {

	private final int amount;

	public MonthlyInvestmentAmount(int amount) {
		this.amount = amount;
		if (amount <= 0) {
			throw new IllegalArgumentException("월 투자 금액은 0보다 커야 합니다.");
		}
	}

	@Override
	public int calMonthsToReach(int targetAmount) {
		if (amount >= targetAmount) {
			return 1;
		}
		if (targetAmount % amount == 0) {
			return (targetAmount / amount);
		}
		return (targetAmount / amount) + 1;
	}
}

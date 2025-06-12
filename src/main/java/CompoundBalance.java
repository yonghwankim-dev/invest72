public class CompoundBalance implements Balance {
	private final InvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;

	public CompoundBalance(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
	}

	@Override
	public int getTotalPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getInterestAmount() {
		int balanceValue = getBalanceValue();
		return balanceValue - getTotalPrincipal();
	}

	@Override
	public int getBalanceValue() {
		double result = 0;
		for (int i = 0; i < investPeriod.getMonths(); i++){
			result += investmentAmount.getAmount();
			result *= getGrowthFactor(interestRate);
		}
		return (int)result;
	}

	private double getGrowthFactor(InterestRate interestRate) {
		return 1 + interestRate.getMonthlyRate();
	}
}

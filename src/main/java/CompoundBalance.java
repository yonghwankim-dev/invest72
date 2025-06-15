public class CompoundBalance implements Balance {
	private final InvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public CompoundBalance(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate,
		Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.investPeriod = investPeriod;
		this.interestRate = interestRate;
		this.taxable = taxable;
	}

	@Override
	public int getTotalPrincipal() {
		return investPeriod.getTotalPrincipal(investmentAmount);
	}

	@Override
	public int getInterestAmount() {
		return getPreTaxAmount() - getTotalPrincipal();
	}

	@Override
	public int getAmount() {
		int preTaxAmount = getPreTaxAmount();
		int tax = taxable.applyTax(getInterestAmount());
		return preTaxAmount - tax;
	}

	private int getPreTaxAmount() {
		double result = 0;
		for (int i = 0; i < investPeriod.getMonths(); i++){
			result = addMonthlyInvestmentTo(result);
			result = applyMonthlyInterest(result);
		}
		return (int)result;
	}

	private double addMonthlyInvestmentTo(double currentBalance) {
		return currentBalance + investmentAmount.getAmount();
	}

	private double applyMonthlyInterest(double currentBalance) {
		return currentBalance * getGrowthFactor(interestRate);
	}

	private double getGrowthFactor(InterestRate interestRate) {
		return 1 + interestRate.getMonthlyRate();
	}
}

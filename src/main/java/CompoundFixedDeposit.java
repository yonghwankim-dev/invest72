public class CompoundFixedDeposit implements Investment {

	private final LumpSumInvestmentAmount investmentAmount;
	private final InterestRate interestRate;
	private final InvestPeriod investPeriod;
	private final Taxable taxable;

	public CompoundFixedDeposit(LumpSumInvestmentAmount investmentAmount, InterestRate interestRate, InvestPeriod investPeriod, Taxable taxable) {
		this.investmentAmount = investmentAmount;
		this.interestRate = interestRate;
		this.investPeriod = investPeriod;
		this.taxable = taxable;
	}

	/**
	 * 예금 투자 금액을 계산하여 원금과 이자를 합한 최종 금액을 반환합니다.
	 * 이자는 복리로 계산되며, 세금이 적용됩니다.
	 * <p>
	 * 이자 = 원금 * (1 + 월이자율) ^ 투자기간(개월) - 원금
	 * </p>
	 * <p>
	 * 세금 = 이자 * 세율
	 * </p>
	 * <p>
	 * 최종 금액 = 원금 + 이자 - 세금
	 * </p>
	 */
	@Override
	public int getAmount() {
		int amount = investmentAmount.getDepositAmount();
		int interest = calInterest();
		int tax = taxable.applyTax(interest);
		return amount + interest - tax;
	}

	private int calInterest() {
		int amount = investmentAmount.getDepositAmount();
		double totalGrowthFactor = interestRate.calTotalGrowthFactor(investPeriod);
		return (int)(Math.round(amount * totalGrowthFactor) - amount);
	}
}

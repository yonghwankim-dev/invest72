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
	 * 투자 기간 동안의 총 원금(투자금액)을 반환합니다.
	 * 이자 = 투자금액 * (1 + 월이율) ^ 투자기간(월)
	 */
	@Override
	public int getAmount() {
		int amount = investmentAmount.getDepositAmount();// 원금
		double growthFactor = 1 + interestRate.getMonthlyRate(); // 성장 계수
		double totalGrowthFactor = Math.pow(growthFactor, investPeriod.getMonths()); // 총 성장 계수

		int interest = (int)(Math.round(amount * totalGrowthFactor) - amount);
		int tax = taxable.applyTax(interest);// 세금 적용
		return amount + interest - tax;
	}
}

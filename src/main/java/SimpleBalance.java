/**
 * 단리로 계산되는 잔액을 나타내는 클래스입니다.
 */
public class SimpleBalance implements Balance {

	private final InvestmentAmount investmentAmount;
	private final InvestPeriod investPeriod;
	private final InterestRate interestRate;
	private final Taxable taxable;

	public SimpleBalance(InvestmentAmount investmentAmount, InvestPeriod investPeriod, InterestRate interestRate,
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
		double totalInterest = 0;
		for (int month = 0; month < investPeriod.getMonths(); month++) {
			double yearsInvested = investPeriod.getYearsInvested(month);
			totalInterest += (investmentAmount.getAnnualInterest(interestRate) * yearsInvested);
		}
		return (int)totalInterest;
	}

	@Override
	public int getAmount() {
		int interestAmount = getInterestAmount();
		int tax = taxable.applyTax(interestAmount);
		return getTotalPrincipal() + interestAmount - tax;
	}
}

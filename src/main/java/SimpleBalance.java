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
		int totalMonths = investPeriod.getMonths();
		for (int month = 0; month < totalMonths; month++) {
			double yearsInvested = getYearsInvested(month);
			totalInterest += (investmentAmount.getAmount() * interestRate.getAnnualRate() * yearsInvested);
		}
		return (int)totalInterest;
	}

	private double getYearsInvested(int currentMonth) {
		return (investPeriod.getMonths() - currentMonth) / 12.0;
	}

	@Override
	public int getAmount() {
		return getTotalPrincipal() + getInterestAmount();
	}
}

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
		return 0;
	}

	@Override
	public int getAmount() {
		int totalMonths = investPeriod.getMonths();
		double totalInterest = 0;
		int totalPrincipal = 0;
		for (int month = 0; month < totalMonths; month++) {
			double yearsInvested = (totalMonths - month) / 12.0;
			double interest = investmentAmount.getAmount() * interestRate.getAnnualRate() * yearsInvested;
			totalInterest += interest;
			totalPrincipal += investmentAmount.getAmount();
		}
		return (int) (totalPrincipal + totalInterest);
	}
}

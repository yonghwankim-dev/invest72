import java.text.DecimalFormat;

public class Invest72Application {
	public static void main(String[] args) {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_500_000);
		InvestPeriod investPeriod = new YearlyInvestPeriod(5);
		InterestRate interestRate = new AnnualInterestRate(0.10);
		Taxable taxable = new StandardTax(0.154);
		Investment investment = new SimpleFixedInstallmentSaving(
			investmentAmount,
			investPeriod,
			interestRate,
			taxable
		);
		int amount = investment.getAmount();
		System.out.println(new DecimalFormat("#,###").format(amount) + "원");
	}
}

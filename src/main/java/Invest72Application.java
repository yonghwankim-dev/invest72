import java.text.DecimalFormat;

import interest_rate.AnnualInterestRate;
import interest_rate.InterestRate;
import invest_amount.InstallmentInvestmentAmount;
import invest_amount.MonthlyInstallmentInvestmentAmount;
import invest_period.InvestPeriod;
import invest_period.YearlyInvestPeriod;
import investment.Investment;
import investment.SimpleFixedInstallmentSaving;
import tax.StandardTax;
import tax.Taxable;

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

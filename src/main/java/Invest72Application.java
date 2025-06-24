import java.text.DecimalFormat;

import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.invest_amount.InstallmentInvestmentAmount;
import domain.invest_amount.MonthlyInstallmentInvestmentAmount;
import domain.invest_period.InvestPeriod;
import domain.invest_period.YearlyInvestPeriod;
import domain.investment.Investment;
import domain.investment.SimpleFixedInstallmentSaving;
import domain.tax.FixedTaxRate;
import domain.tax.StandardTax;
import domain.tax.Taxable;

public class Invest72Application {
	public static void main(String[] args) {
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1_500_000);
		InvestPeriod investPeriod = new YearlyInvestPeriod(5);
		InterestRate interestRate = new AnnualInterestRate(0.10);
		Taxable taxable = new StandardTax(new FixedTaxRate(0.154));
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

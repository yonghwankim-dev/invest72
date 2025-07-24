package application;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import domain.amount.InstallmentInvestmentAmount;
import domain.amount.MonthlyInstallmentInvestmentAmount;
import domain.interest_rate.AnnualInterestRate;
import domain.interest_rate.InterestRate;
import domain.tax.FixedTaxRate;
import domain.tax.Taxable;
import domain.tax.factory.KoreanTaxableFactory;
import domain.tax.factory.TaxableFactory;

class TargetAchievementInvestmentCalculatorTest {

	@Test
	void calMonth() {
		int targetAmount = 10000000;
		InstallmentInvestmentAmount investmentAmount = new MonthlyInstallmentInvestmentAmount(1000000);
		InterestRate interestRate = new AnnualInterestRate(0.05);
		TaxableFactory taxableFactory = new KoreanTaxableFactory();
		Taxable taxable = taxableFactory.createStandardTax(new FixedTaxRate(0.154));
		InvestmentCalculator calculator = new TargetAchievementInvestmentCalculator(targetAmount, investmentAmount,
			interestRate, taxable);

		int month = calculator.calMonth();

		Assertions.assertEquals(10, month);
	}

}

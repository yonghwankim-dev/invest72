import java.time.Period;

public class CompoundMonthlyInvestmentCalculator implements InvestmentCalculator {
	@Override
	public InvestmentSummary calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate) {
		if (monthlyInvestment < 0) {
			throw new IllegalArgumentException("Monthly investment must be non-negative.");
		}
		if (investmentPeriod < 0) {
			throw new IllegalArgumentException("Investment period must be greater than zero.");
		}
		double monthlyRate = getMonthlyRate(annualInterestRate);

		double balance = getBalance(monthlyInvestment, investmentPeriod, monthlyRate);
		int principal = getTotalPrincipal(monthlyInvestment, investmentPeriod);
		int interest = getInterest(balance, principal);
		return new CompoundMonthlyInvestmentSummary(principal, interest);
	}

	private double getBalance(int monthlyInvestment, int investmentPeriod, double monthlyRate) {
		double balance = 0;
		for (int i = 0; i < investmentPeriod; i++){
			balance += monthlyInvestment;
			balance *= applyMonthlyRate(monthlyRate);
		}
		return balance;
	}

	private int getTotalPrincipal(int monthlyInvestment, int investmentPeriod) {
		return monthlyInvestment * investmentPeriod;
	}

	private double getMonthlyRate(double annualInterestRate) {
		return annualInterestRate / 12;
	}

	private double applyMonthlyRate(double monthlyRate) {
		return 1 + monthlyRate;
	}

	private int getInterest(double balance, double totalPrincipal) {
		return (int)(balance - totalPrincipal);
	}

	@Override
	public InvestmentSummary calculate(int monthlyInvestment, Period investmentPeriod, double annualInterestRate) {
		int months = (int) investmentPeriod.toTotalMonths();
		return calculate(monthlyInvestment, months, annualInterestRate);
	}
}

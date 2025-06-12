import java.time.Period;

public interface InvestmentCalculator {
	InvestmentSummary calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate);
}

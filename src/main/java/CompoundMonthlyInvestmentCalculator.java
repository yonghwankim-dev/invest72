public class CompoundMonthlyInvestmentCalculator implements InvestmentCalculator {
	@Override
	public InvestmentSummary calculate(int monthlyInvestment, int investmentPeriod, double annualInterestRate) {
		double monthlyRate = annualInterestRate / 12.0;
		double totalPrincipal = 0;
		double balance = 0;

		for (int i = 0; i < investmentPeriod; i++){
			balance += monthlyInvestment;
			balance *= (1 + monthlyRate);
			totalPrincipal += monthlyInvestment;
		}

		int principal = (int) totalPrincipal;
		int interest = (int)(balance - totalPrincipal);
		return new CompoundMonthlyInvestmentSummary(principal, interest);
	}
}

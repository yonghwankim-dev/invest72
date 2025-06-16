public interface InvestPeriod {
	int getMonths();

	int getTotalPrincipal(InstallmentInvestmentAmount investmentAmount);

	double getYearsInvested(int currentMonth);
}

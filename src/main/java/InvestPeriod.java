public interface InvestPeriod {
	int getMonths();

	int getTotalPrincipal(InvestmentAmount investmentAmount);

	double getYearsInvested(int currentMonth);
}

package application.response;

import java.util.List;
import java.util.Objects;

public class CalculateMonthlyInvestmentResponse {
	private final List<MonthlyInvestmentResult> monthlyInvestmentResults;

	public CalculateMonthlyInvestmentResponse(List<MonthlyInvestmentResult> monthlyInvestmentResults) {
		this.monthlyInvestmentResults = monthlyInvestmentResults;
	}

	public List<MonthlyInvestmentResult> getMonthlyInvestmentResults() {
		return monthlyInvestmentResults;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		CalculateMonthlyInvestmentResponse response = (CalculateMonthlyInvestmentResponse)object;
		return Objects.equals(monthlyInvestmentResults, response.monthlyInvestmentResults);
	}

	@Override
	public int hashCode() {
		return Objects.hash(monthlyInvestmentResults);
	}

	@Override
	public String toString() {
		return "CalculateMonthlyInvestmentResponse{" +
			"monthlyInvestmentResults=" + monthlyInvestmentResults +
			'}';
	}
}

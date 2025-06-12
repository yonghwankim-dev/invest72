import java.util.Objects;

public class CompoundMonthlyInvestmentSummary implements InvestmentSummary {
	private final int principal;
	private final int interest;

	public CompoundMonthlyInvestmentSummary(int principal, int interest) {
		this.principal = principal;
		this.interest = interest;
	}

	@Override
	public int getPrincipal() {
		return this.principal;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object)
			return true;
		if (object == null || getClass() != object.getClass())
			return false;
		CompoundMonthlyInvestmentSummary that = (CompoundMonthlyInvestmentSummary)object;
		return principal == that.principal;
	}

	@Override
	public int hashCode() {
		return Objects.hash(principal);
	}
}

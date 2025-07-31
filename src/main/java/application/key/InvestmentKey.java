package application.key;

import co.invest72.investment.domain.interest.InterestType;
import co.invest72.investment.domain.investment.InvestmentType;

public record InvestmentKey(InvestmentType investmentType, InterestType interestType) {
}

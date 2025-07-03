package application.key;

import domain.type.InterestType;
import domain.type.InvestmentType;

public record InvestmentKey(InvestmentType investmentType, InterestType interestType) {
}

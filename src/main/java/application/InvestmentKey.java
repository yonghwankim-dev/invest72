package application;

import domain.type.InterestType;
import domain.type.InvestmentType;

public record InvestmentKey(InvestmentType investmentType, InterestType interestType) {
}

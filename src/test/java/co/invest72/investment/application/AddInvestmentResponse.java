package co.invest72.investment.application;

import co.invest72.investment.domain.Investment;

public record AddInvestmentResponse(Long id, String uid, Investment investment) {

}

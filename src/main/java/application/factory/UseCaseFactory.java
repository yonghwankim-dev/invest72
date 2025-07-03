package application.factory;

import application.usecase.InvestmentUseCase;

public interface UseCaseFactory {
	InvestmentUseCase createCalculateInvestmentUseCase();
}

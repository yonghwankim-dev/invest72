import adapter.console.ConsoleInvestmentRunner;
import application.CalculateInvestmentUseCase;
import application.DefaultInvestmentFactory;
import application.InvestmentFactory;
import application.InvestmentUseCase;

public class Invest72Application {
	public static void main(String[] args) {
		InvestmentFactory investmentFactory = new DefaultInvestmentFactory();
		InvestmentUseCase useCase = new CalculateInvestmentUseCase(investmentFactory);
		ConsoleInvestmentRunner runner = new ConsoleInvestmentRunner(useCase, System.in);
		runner.run();
	}
}

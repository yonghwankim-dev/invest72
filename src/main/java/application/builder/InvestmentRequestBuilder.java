package application.builder;

import static application.request.CalculateInvestmentRequest.*;
import static application.request.TargetAchievementRequest.*;

public interface InvestmentRequestBuilder {
	CalculateInvestmentRequestBuilder calculateInvestmentRequestBuilder();

	TargetAchievementRequestBuilder targetAchievementRequestBuilder();
}

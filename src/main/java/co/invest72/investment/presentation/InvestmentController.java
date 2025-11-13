package co.invest72.investment.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InvestmentController {

	@GetMapping("/investments/calculate/expiration/result")
	public String showExpirationResultPage() {
		return "result";
	}
}

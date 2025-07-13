package application.printer;

import java.io.PrintStream;

import application.response.TargetAchievementResponse;

public class PrintStreamBasedTargetAchievementResultPrinter implements TargetAchievementResultPrinter {

	private final PrintStream out;

	public PrintStreamBasedTargetAchievementResultPrinter(PrintStream out) {
		this.out = out;
	}

	@Override
	public void printResult(TargetAchievementResponse response) {
		out.println("목표 달성 날짜: " + response.getAchievedDate());
		out.println("원금: " + formatAmount(response.getPrincipal()));
		out.println("이자: " + formatAmount(response.getInterest()));
		out.println("세금: " + formatAmount(response.getTax()));
		out.println("세후 이자: " + formatAmount(response.getAfterTaxInterest()));
		out.println("총 수익: " + formatAmount(response.getTotalProfit()));
	}

	private String formatAmount(int amount) {
		return String.format("%,d원", amount);
	}
}

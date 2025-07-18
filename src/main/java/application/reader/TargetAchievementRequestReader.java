package application.reader;

import java.io.BufferedReader;
import java.io.IOException;

import adapter.ui.GuidePrinter;

public class TargetAchievementRequestReader implements TargetAmountReader {

	private final BufferedReader reader;
	private final GuidePrinter guidePrinter;

	public TargetAchievementRequestReader(BufferedReader reader, GuidePrinter guidePrinter) {
		this.reader = reader;
		this.guidePrinter = guidePrinter;
	}

	@Override
	public int readTargetAmount() throws IOException {
		guidePrinter.printTargetAmountInputGuide();
		return Integer.parseInt(reader.readLine());
	}
}

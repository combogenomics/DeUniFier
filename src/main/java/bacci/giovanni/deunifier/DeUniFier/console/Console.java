package bacci.giovanni.deunifier.DeUniFier.console;

import java.util.Scanner;

public abstract class Console {
	private Scanner scan = null;
	private String init = "DeUniFier> ";

	public Console() {
		this.scan = new Scanner(System.in);
	}

	private void init() {
		System.out.printf("%s", this.init);
	}

	protected void waitForInput() {
		init();
		while (this.scan.hasNext()) {
			String line = this.scan.nextLine();
			act(line);
			init();
		}
	}

	protected abstract void act(String paramString);
}
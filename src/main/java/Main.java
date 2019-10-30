public class Main {

	public static void main(String[] args) throws InterruptedException {
		Simulator simulator = new Simulator(500, 500);
		Window window = new Window(simulator);

		while (true) {
			simulator.tick();
			window.updateDisplay();
			Thread.sleep(1);
		}

	}
}

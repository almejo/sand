import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;

public class Window extends JFrame {
	private final Display display;

	Window(Simulator simulator) {
		super("Falling Sand");
		setPreferredSize(new Dimension(simulator.getWidth() * Display.PIXEL_SIDE + 50
				, simulator.getHeight() * Display.PIXEL_SIDE + 50));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		display = new Display(simulator);
		setLayout(new BorderLayout());
		add(display, BorderLayout.CENTER);
		add(new ToolsPanel(display), BorderLayout.EAST);
		pack();
		setVisible(true);
	}

	public void draw() {
		display.draw();
	}

	public void updateDisplay() {
		display.repaint();
	}
}

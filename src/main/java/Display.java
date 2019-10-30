import javax.swing.JSlider;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Display extends Container implements MouseListener
		, MouseMotionListener {
	static final int PIXEL_SIDE = 2;
	private Simulator simulator;
	private boolean drawing;
	private int x;
	private int y;
	private Material tool = Material.SAND;
	private int ticks = 5500;

	Display(Simulator simulator) {
		this.simulator = simulator;
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}

	@Override
	public void paint(Graphics graphics) {
		Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(Color.BLACK);
		graphics2D.fillRect(0, 0
				, simulator.getWidth() * PIXEL_SIDE, simulator.getHeight() * PIXEL_SIDE);
		for (int x = 0; x < simulator.getWidth(); x++) {
			for (int y = 0; y < simulator.getHeight(); y++) {
				if (simulator.getValue(x, y) != Material.EMPTY) {
					graphics2D.setColor(simulator.getValue(x, y).getColor());
					graphics2D.fillRect(x * PIXEL_SIDE, y * PIXEL_SIDE, PIXEL_SIDE, PIXEL_SIDE);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent mouseEvent) {

	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		drawing = true;
		x = mouseEvent.getX();
		y = mouseEvent.getY();
	}

	@Override
	public void mouseReleased(MouseEvent mouseEvent) {
		drawing = false;
	}

	@Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	@Override
	public void mouseDragged(MouseEvent mouseEvent) {
		x = mouseEvent.getX();
		y = mouseEvent.getY();
		draw();
	}

	@Override
	public void mouseMoved(MouseEvent mouseEvent) {
		x = Math.min(mouseEvent.getX(), getWidth() - 1);
		y = Math.min(mouseEvent.getY(), getHeight() - 1);
		draw();
	}

	void draw() {
		if (drawing) {
			simulator.set(x / PIXEL_SIDE, y / PIXEL_SIDE, tool);
		}
	}

	public void setTool(Material tool) {
		this.tool = tool;
	}

	public void setTicks(int value) {
		simulator.setTicks(value);
	}
}

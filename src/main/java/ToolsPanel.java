
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class ToolsPanel extends JPanel implements ChangeListener {

	private Display display;

	ToolsPanel(Display display) {
		this.display = display;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		Arrays.asList(Material.values()).forEach(material -> {
					add(newButton(display, material.name(), material));
				}
		);
		JSlider slider = new JSlider();
		slider.setMinimum(100);
		slider.setMaximum(10000);
		slider.setValue(5000);
		slider.setMajorTickSpacing(1000);
		slider.setMinorTickSpacing(100);
		slider.setPaintTicks(true);
		slider.addChangeListener(this);
		add(slider);
		display.setTicks(slider.getValue());
	}

	private JButton newButton(Display display, String label, Material tool) {
		JButton button = new JButton(label);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent mouseEvent) {
				display.setTool(tool);
			}
		});
		return button;
	}

	@Override
	public void stateChanged(ChangeEvent changeEvent) {
		this.display.setTicks(((JSlider) changeEvent.getSource()).getValue());
	}
}

import java.awt.Color;

public enum Material {
	EMPTY(Color.BLACK),
	SAND(Color.ORANGE),
	WATER(Color.BLUE),
	STEAM(new Color(200, 200,255)),
	PLANT(Color.GREEN),
	STONE(Color.GRAY),
	FIRE(Color.RED),
	SAND_GENERATOR(Color.ORANGE),
	WATER_GENERATOR(Color.BLUE),
	FIRE_GENERATOR(Color.RED);

	private Color color;

	Material(Color color) {
		this.color = color;
	}

	Color getColor() {
		return color;
	}
}

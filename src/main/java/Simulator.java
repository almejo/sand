import java.util.Random;

class Simulator {

	private final Material[][] data;
	private final Random random;
	private int width;
	private int height;
	private int ticks;

	Simulator(int width, int height) {
		random = new Random();
		this.width = width;
		this.height = height;
		data = new Material[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				data[i][j] = Material.EMPTY;
			}
		}
	}

	int getWidth() {
		return width;
	}

	int getHeight() {
		return height;
	}

	Material getValue(int x, int y) {
		return data[x][y];
	}

	public void tick() {
		for (int i = 0; i < ticks; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(width);
			switch (data[x][y]) {
				case SAND:
					doSand(x, y);
					break;
				case FIRE:
					doFire(x, y);
					break;
				case WATER:
					doWater(x, y);
					break;
				case STEAM:
					doSteam(x, y);
					break;
				case PLANT:
					doPlant(x, y);
					break;
				case SAND_GENERATOR:
					doGenerator(x, y, Material.SAND);
					break;
				case FIRE_GENERATOR:
					doAroundGenerator(x, y, Material.FIRE);
					break;
				case WATER_GENERATOR:
					doGenerator(x, y, Material.WATER);
					break;
			}
		}
	}

	private void doSteam(int x, int y) {
		doFluid(x, y, -1);
	}

	private void doPlant(int x, int y) {
		if (testWater(x, y + 1)) {
			data[x][y + 1] = Material.PLANT;
		} else if (testWater(x + 1, y + 1)) {
			data[x + 1][y + 1] = Material.PLANT;
		} else if (testWater(x + 1, y)) {
			data[x + 1][y] = Material.PLANT;
		} else if (testWater(x + 1, y - 1)) {
			data[x + 1][y - 1] = Material.PLANT;
		} else if (testWater(x, y - 1)) {
			data[x][y - 1] = Material.PLANT;
		} else if (testWater(x - 1, y - 1)) {
			data[x - 1][y - 1] = Material.PLANT;
		} else if (testWater(x - 1, y)) {
			data[x - 1][y] = Material.PLANT;
		} else if (testWater(x - 1, y + 1)) {
			data[x - 1][y + 1] = Material.PLANT;
		}
	}

	private void doFire(int x, int y) {
		if (test(x, y + 1, Material.PLANT)) {
			data[x][y + 1] = Material.FIRE;
		}
		if (test(x + 1, y + 1, Material.PLANT)) {
			data[x + 1][y + 1] = Material.FIRE;
		}
		if (test(x + 1, y, Material.PLANT)) {
			data[x + 1][y] = Material.FIRE;
		}
		if (test(x + 1, y - 1, Material.PLANT)) {
			data[x + 1][y - 1] = Material.FIRE;
		}
		if (test(x, y - 1, Material.PLANT)) {
			data[x][y - 1] = Material.FIRE;
		}
		if (test(x - 1, y - 1, Material.PLANT)) {
			data[x - 1][y - 1] = Material.FIRE;
		}
		if (test(x - 1, y, Material.PLANT)) {
			data[x - 1][y] = Material.FIRE;
		}
		if (test(x - 1, y + 1, Material.PLANT)) {
			data[x - 1][y + 1] = Material.FIRE;
		}
		data[x][y] = Material.EMPTY;
		if (test(x, y - 1, Material.WATER)) {
			data[x][y - 1] = Material.STEAM;
		}
	}

	private boolean testWater(int x, int y) {
		return test(x, y, Material.WATER) || test(x, y, Material.WATER_GENERATOR);
	}

	private void doGenerator(int x, int y, Material material) {
		if (isEmpty(x, y + 1) || canMove(x, y + 1)) {
			data[x][y + 1] = material;
		}
	}

	private void doAroundGenerator(int x, int y, Material material) {
		doGenerator(x + random.nextInt(3) - 2, y + random.nextInt(3) - 2, material);
	}

	private void doWater(int x, int y) {
		doFluid(x, y, 1);
	}

	private void doFluid(int x, int y, int direction) {
		if (isEmpty(x, y + direction)) {
			move(x, y, x, y + direction);
			return;
		}
		if (random.nextInt(100) < 50) {
			if (!checkRight(x, y, direction)) {
				checkLeft(x, y, direction);
			}
		} else {
			if (!checkLeft(x, y, direction)) {
				checkRight(x, y, direction);
			}
		}

	}

	private boolean checkLeft(int x, int y, int yDirection) {
		for (int i = x - 1; i >= 0; i--) {
			if (!isEmpty(i, y)) {
				return false;
			}
			if (isEmpty(i, y + yDirection)) {
				move(x, y, i, y + yDirection);
				return true;
			}
		}
		return false;
	}

	private boolean checkRight(int x, int y, int yDirection) {
		for (int i = x + 1; i < width; i++) {
			if (!isEmpty(i, y)) {
				return false;
			}
			if (isEmpty(i, y + yDirection)) {
				move(x, y, i, y + yDirection);
				return true;
			}
		}
		return false;
	}

	private void doSand(int x, int y) {
		if (canMove(x, y + 1)) {
			move(x, y, x, y + 1);
		} else if (canMove(x + 1, y + 1)) {
			move(x, y, x + 1, y + 1);
		} else if (canMove(x - 1, y + 1)) {
			move(x, y, x - 1, y + 1);
		}
	}

	private boolean canMove(int x, int y) {
		return isEmpty(x, y) || test(x, y, Material.WATER);
	}

	private boolean test(int x, int y, Material material) {
		if (x >= width || x < 0 || y >= height || y < 0) {
			return false;
		}
		return data[x][y] == material;
	}

	private boolean isEmpty(int x, int y) {
		if (x >= width || x < 0 || y >= height || y < 0) {
			return false;
		}

		return data[x][y] == Material.EMPTY;
	}

	private void move(int x, int y, int nextX, int nextY) {
		Material value = data[x][y];
		data[x][y] = data[nextX][nextY];
		data[nextX][nextY] = value;
	}

	void set(int x, int y, Material material) {
		data[x][y] = material;
	}

	public void setTicks(int value) {
		ticks = value;
	}
}

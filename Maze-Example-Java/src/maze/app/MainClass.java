package maze.app;

public class MainClass {
	static final String[] MAP = {
			"##########",
			"#        #",
			"### ### ##",
			"#    #   #",
			"## # ## ##",
			"#  #  # ##",
			"# ###    #",
			"# # # ## #",
			"#     #  #",
			"##########",
		};
	static final int
		MAP_WIDTH = 10,
		MAP_HEIGHT = 10,
		START_X = 1, // начальная строка
		START_Y = 1, // начальный столбец
		FINISH_X = 8,
		FINISH_Y = 8;
	static final int
		EMPTY = 0,
		WALL = -1,
		PATH = -2;
	
	public static void main(String[] args) {
		int[][] work_map = new int[MAP_HEIGHT][];
		for (int row = 0; row < MAP_HEIGHT; ++row)
			work_map[row] = new int[MAP_WIDTH];
		
		cook_map(work_map);
		
		int iter = 1;
		work_map[START_X][START_Y] = iter;
		boolean found;
		do {
			found = false;
			for (int row = 1; row < MAP_HEIGHT-1; ++row) {
				for (int col = 1; col < MAP_WIDTH-1; ++col) {
					if (work_map[row][col] == iter) {
						found = true;
						if (work_map[row][col-1] == EMPTY)
							work_map[row][col-1] = iter + 1;
						if (work_map[row][col+1] == EMPTY)
							work_map[row][col+1] = iter + 1;
						if (work_map[row-1][col] == EMPTY)
							work_map[row-1][col] = iter + 1;
						if (work_map[row+1][col] == EMPTY)
							work_map[row+1][col] = iter + 1;
					}
				}
			}
			if (work_map[FINISH_X][FINISH_Y] != EMPTY)
				break;
			++iter;
		} while (found);
		
		if (! found) {
			System.out.println("Нет пути!");
		} else {
			draw_path(work_map);
		}
		
		print_map(work_map);
	}

	private static void draw_path(int[][] work_map) {
		int iter;
		int x = FINISH_X, y = FINISH_Y;
		while ((iter = work_map[x][y]) != 1) {
			work_map[x][y] = PATH;
			if (work_map[x][y-1] == iter-1) { --y; continue; }
			if (work_map[x][y+1] == iter-1) { ++y; continue; }
			if (work_map[x-1][y] == iter-1) { --x; continue; }
			if (work_map[x+1][y] == iter-1) { ++x; continue; }
		}
		work_map[x][y] = PATH;
	}

	private static void print_map(int[][] work_map) {
		for (int row = 0; row < MAP_HEIGHT; ++row) {
			for (int col = 0; col < MAP_WIDTH; ++col) {
				switch (work_map[row][col]) {
				case WALL:
					System.out.print("\u2588\u2588"); // ██
					break;
				case PATH:
					System.out.print("\u2591\u2591"); // ░░
					break;
				default:
					System.out.print("  ");
				}
			}
			System.out.println();
		}
	}

	private static void cook_map(int[][] work_map) {
		for (int row = 0; row < MAP_HEIGHT; ++row) {
			for (int col = 0; col < MAP_WIDTH; ++col) {
				if (MAP[row].charAt(col) == '#')
					work_map[row][col] = WALL;
				else
					work_map[row][col] = EMPTY;
			}
		}
	}

}

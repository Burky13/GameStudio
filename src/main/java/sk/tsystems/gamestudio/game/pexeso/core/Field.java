package sk.tsystems.gamestudio.game.pexeso.core;

import sk.tsystems.gamestudio.game.puzzle.core.Tile;

public class Field {
	private int rowCoutnt;

	private int columnCount;

	private Tile[][] tiles;

	/**
	 * @param rowCoutnt
	 * @param columnCount
	 * @param tiles
	 */
	public Field(int rowCoutnt, int columnCount) {
		super();
		this.rowCoutnt = rowCoutnt;
		this.columnCount = columnCount;
		tiles = new Tile[rowCoutnt][columnCount];
	}

	private void fillField() {
		int value = 1;
		for (int row = 0; row < rowCoutnt; row++) {
			for (int column = 0; column < columnCount; column++) {
				tiles[row][column] = new Tile(value);
				value++;
				while (!(rowCoutnt <= rowCoutnt / 2)) {
					
				}
			}
		}
	}
}

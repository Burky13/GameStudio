package sk.tsystems.gamestudio.game.mines.core;

import java.util.Random;

public class Field {
	private final int rowCount;

	private final int columnCount;

	private final int mineCount;

	private final Tile[][] tiles;

	int numberOfOpenTiles;

	private GameState state = GameState.PLAYING;

	public Field(int rowCount, int columnCount, int mineCount) {
		// throw new IllegalArgumentException("Invalid number of mines " + mineCount);
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		this.mineCount = mineCount;
		tiles = new Tile[rowCount][columnCount];
		generate();
	}

	private void generate() {
		generateMines();
		fillWithClues();
	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				if (tiles[row][column] == null) {
					tiles[row][column] = new Clue(countAdjacentMines(row, column));
				}
			}
		}
	}

	public int countAdjacentMines(int row, int column) {
		int count = 0;
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						if (tiles[actRow][actColumn] instanceof Mine) {
							count++;
						}
					}
				}
			}
		}
		return count;

	}

	public void markTile(int row, int column) {
		Tile tile = tiles[row][column];
		if (tile.getState() == TileState.CLOSED) {
			tile.setState(TileState.MARKED);
		} else if (tile.getState() == TileState.MARKED) {
			tile.setState(TileState.CLOSED);
		}
	}

	public void openTile(int row, int column) {
		Tile tile = tiles[row][column];

		if (tile.getState() == TileState.CLOSED) {
			tile.setState(TileState.OPENED);
			numberOfOpenTiles++;

			if (tile instanceof Mine) {
				state = GameState.FAILED;
			} else if (tile instanceof Clue && ((Clue) tile).getValue() == 0) {
				openAdjacentTiles(row, column);
			} 
			if (isSolved())
				state = GameState.SOLVED;
		}
	}

	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						openTile(actRow, actColumn);
					}
				}
			}
		}
	}

	private boolean isSolved() {
		return rowCount * columnCount - numberOfOpenTiles == mineCount;
	}

	private void generateMines() {
		Random random = new Random();

		int minesToSet = mineCount;
		while (minesToSet > 0) {
			int row = random.nextInt(rowCount);
			int column = random.nextInt(columnCount);
			if (tiles[row][column] == null) {
				tiles[row][column] = new Mine();
				minesToSet--;
			}
		}
	}

	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public int getMineCount() {
		return mineCount;
	}

	public GameState getState() {
		return state;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}
}

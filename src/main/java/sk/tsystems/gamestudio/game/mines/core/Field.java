package sk.tsystems.gamestudio.game.mines.core;

import java.util.Formatter;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import sk.tsystems.gamestudio.game.mines.ui.MinesConsoleUI;

public class Field {
	private final int rowCount;

	private final int columnCount;

	private final int mineCount;

	private final Tile[][] tiles;

	int numberOfOpenTiles;
	
    private long startMillis;

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
		startMillis = System.currentTimeMillis();
	}

	private void fillWithClues() {
		for (int row = 0; row < rowCount; row++) {
			for (int column = 0; column < columnCount; column++) {
				final Tile tile = getTile(row, column);
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
        if (state == GameState.PLAYING) {
            final Tile tile = tiles[row][column];
            if (tile.getState() == TileState.CLOSED) {
                tile.setState(TileState.MARKED);
            } else if (tile.getState() == TileState.MARKED) {
                tile.setState(TileState.CLOSED);
            }
        }
    }


	public void openTile(int row, int column) {
        if (state == GameState.PLAYING) {
            final Tile tile = tiles[row][column];
            if (tile.getState() == TileState.CLOSED) {
                tile.setState(TileState.OPENED);
                numberOfOpenTiles++;

                if (tile instanceof Mine) {
                    state = GameState.FAILED;
                    return;
                }

                if (((Clue) tile).getValue() == 0)
                    openAdjacentTiles(row, column);
                if (isSolved()) {
                    state = GameState.SOLVED;
                }
            }
        }
    }


	private void openAdjacentTiles(int row, int column) {
		for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
			int actRow = row + rowOffset;
			if (actRow >= 0 && actRow < rowCount) {
				for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
					int actColumn = column + columnOffset;
					if (actColumn >= 0 && actColumn < columnCount) {
						openTileWithCheck(actRow, actColumn);
					}
				}
			}
		}
	}

	private boolean isSolved() {
		return rowCount * columnCount - numberOfOpenTiles == mineCount;
	}

	private void openTileWithCheck(int row, int column) {
        if (row >= 0 && row < rowCount && column >= 0 && column < columnCount)
            openTile(row, column);
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
	
	@Override
    public String toString() {
        Formatter sb = new Formatter();
        for (int row = 0; row < rowCount; row++) {
            sb.format("%3s", (char) (row + 'A'));
            for (int column = 0; column < columnCount; column++) {
                final Tile tile = getTile(row, column);
                sb.format("%3s", tile);
            }
            sb.format("%n");
        }
        return sb.toString();
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
	
	public int getPlayingTime() {
        return ((int) (System.currentTimeMillis() - startMillis)) / 1000;
    }

    public int getScore() {
        return rowCount * columnCount - getPlayingTime();
    }

}

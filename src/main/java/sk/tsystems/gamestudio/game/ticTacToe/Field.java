package sk.tsystems.gamestudio.game.ticTacToe;

public class Field {

	private final int rowCount;
	private final int columnCount;
	private final Tile[][] tiles;
	private boolean player1Turn = true;
	private int player1Count;
	private int player2Count;
	private GameState state = GameState.PLAYING;
	private int drawCount;

	public Field(int rowCount, int columnCount) {
		this.rowCount = rowCount;
		this.columnCount = columnCount;
		tiles = new Tile[rowCount][columnCount];
	}

	public void checkField(int row, int column) {
			checkColumns(column);
			checkRows(row);
			checkLeftDiagonal(row, column);
			checkRightDiagonal(row, column);
//			checkDiagonal(row, column, -1);
//			checkDiagonal(row, column, 1);
	}

//	private void checkDiagonal(int row, int column, int dc) {
//		// check left diagonal
//		player1Count = 0;
//		player2Count = 0;
//		int actRow = row;
//		int actColumn = column;
//		while (isInnerTile(actRow, actColumn)) {
//			actRow--;
//			actColumn -= dc;
//		}
//		while (actRow < rowCount - 1 && actColumn < columnCount - 1 ) {
//			if (tiles[actRow][actColumn] instanceof Player1) {
//				player1Count++;
//				hasWon();
//			} else
//				player1Count = 0;
//			if (tiles[actRow][actColumn] instanceof Player2) {
//				player2Count++;
//				hasWon();
//			} else
//				player2Count = 0;
//			actRow++;
//			actColumn += dc;
//		}
//	}
	
//	private boolean isInnerTile(int row, int column) {
//		return row >0 && column > 0 && row < rowCount - 1 && column < columnCount - 1;
//	}

	private void checkLeftDiagonal(int row, int column) {
		// check left diagonal
		player1Count = 0;
		player2Count = 0;
		int actRow = row;
		int actColumn = column;

//		int min = Math.min(row, column);
//		actRow = row - min;
//		actColumn = column - min;
		while (actRow > 0 && actColumn > 0) {
			actRow--;
			actColumn += -1;
		}
		while (actRow < rowCount && actColumn < columnCount ) {
			if (tiles[actRow][actColumn] instanceof Player1) {
				player1Count++;
				hasWon();
			} else
				player1Count = 0;
			if (tiles[actRow][actColumn] instanceof Player2) {
				player2Count++;
				hasWon();
			} else
				player2Count = 0;
			actRow++;
			actColumn += 1;
		}
	}

	private void checkRightDiagonal(int row, int column) {
		// check right diagonal
		player1Count = 0;
		player2Count = 0;
		int actRow = row;
		int actColumn = column;
		while (actRow > 0 && actColumn < columnCount - 1) {
			actRow--;
			actColumn++;
		}
//		int min = Math.min(row, columnCount - column);
//		actRow = row - min;
//		actColumn = column + min;
		while (actRow < rowCount && actColumn >= 0) {
			if (tiles[actRow][actColumn] instanceof Player1) {
				player1Count++;
				hasWon();
			} else
				player1Count = 0;
			if (tiles[actRow][actColumn] instanceof Player2) {
				player2Count++;
				hasWon();
			} else
				player2Count = 0;
			actRow++;
			actColumn--;
		}
	}

	private void checkRows(int row) {
		// check rows
		player1Count = 0;
		player2Count = 0;
		for (int actColumn = 0; actColumn < columnCount; actColumn++) {
			if (tiles[row][actColumn] instanceof Player1) {
				player1Count++;
				hasWon();
			} else
				player1Count = 0;
			if (tiles[row][actColumn] instanceof Player2) {
				player2Count++;
				hasWon();
			} else
				player2Count = 0;
		}
	}

	private void checkColumns(int column) {
		// check columns
		player1Count = 0;
		player2Count = 0;
		for (int actRow = 0; actRow < rowCount; actRow++) {
			if (tiles[actRow][column] instanceof Player1) {
				player1Count++;
				hasWon();
			} else
				player1Count = 0;
			if (tiles[actRow][column] instanceof Player2) {
				player2Count++;
				hasWon();
			} else
				player2Count = 0;
		}
	}

	public void hasWon() {
		if (player1Count == 5) {
			state = GameState.PLAYER1WON;
		} else if (player2Count == 5) {
			state = GameState.PLAYER2WON;
		} else if (drawCount == rowCount * columnCount) {
			state = GameState.DRAW;
		}
	}

	public void markTile(int row, int column) {
		if (tiles[row][column] == null) {
			if (player1Turn) {
				tiles[row][column] = new Player1();
				player1Turn = false;
				drawCount++;
			} else {
				tiles[row][column] = new Player2();
				player1Turn = true;
				drawCount++;
			}
		} else
			return;
	}


	public int getRowCount() {
		return rowCount;
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Tile getTile(int row, int column) {
		return tiles[row][column];
	}

	public GameState getState() {
		return state;
	}

	public String isPlayer1Turn() {
		if(player1Turn) {
			return "Player 1";
		} else return "Player 2";
	}
}

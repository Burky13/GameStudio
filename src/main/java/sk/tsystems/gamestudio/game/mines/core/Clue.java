package sk.tsystems.gamestudio.game.mines.core;

public class Clue extends Tile {
	private final int value;

	public Clue(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	@Override
    public String toString() {
        return super.getState() == TileState.OPENED ?
                String.valueOf(this.value) :
                super.toString();
    }

}

package sk.tsystems.gamestudio.game.mines.core;

public class Mine extends Tile{
	 @Override
	    public String toString() {
	        return super.getState() == TileState.OPENED ?
	                "*" : super.toString();
	    }

}

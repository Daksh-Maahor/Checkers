package checkers;

import checkers.input.MouseManager;
import checkers.states.GameState;

public class Handler {
	
	private Game game;

	public Handler(Game game) {
		this.game = game;
	}
	
	public int getWidth() {
		return game.getWidth();
	}
	
	public int getHeight() {
		return game.getHeight();
	}
	
	public MouseManager getMouseManager() {
		return game.getMouseManager();
	}
	
	public GameState getGameState() {
		return game.getGameState();
	}

}

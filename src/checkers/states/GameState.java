package checkers.states;

import java.awt.Graphics;

import checkers.Handler;
import checkers.board.Board;

public class GameState {
	
	private Handler handler;
	private Board board;

	public GameState(Handler handler) {
		this.handler = handler;
		init();
	}
	
	private void init() {
		board = new Board(handler);
	}
	
	public void tick() {
		board.tick();
	}
	
	public void render(Graphics g) {
		board.render(g);
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

}

package checkers.board;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import checkers.Handler;
import checkers.board.utils.Tuple;
import checkers.piece.Piece;

public class Board {

	public static final int WIDTH = 10;

	public static final Color LIGHT = new Color(238, 238, 210), DARK = new Color(118, 150, 86);

	public static boolean isOutsideBoard(int xy) {
		return (xy >= WIDTH) || (xy < 0);
	}

	private int[][] board;
	private Handler handler;

	private int blackCount, whiteCount, winner;

	private int selectedX, selectedY, selectedPiece;

	private int currentTurn;

	private List<Tuple<Integer, Integer>> possibleMoves;

	private float tileW, tileH;
	
	private int blackScore, whiteScore;

	private boolean gameOver;

	public Board(Handler handler) {
		board = new int[WIDTH][WIDTH];
		this.handler = handler;
		init();

		possibleMoves = new ArrayList<Tuple<Integer, Integer>>();

		resetSelection();

		gameOver = false;
		
		blackScore = (whiteScore = 0);
	}

	private void init() {
		blackCount = (whiteCount = 0);
		for (int x = 0; x < WIDTH; x++) {
			for (int y = 0; y < WIDTH; y++) {
				board[x][y] = -1;
				if (x + y * WIDTH <= WIDTH * (WIDTH / 2 - 1) - 1) {
					if ((x + y) % 2 == 1) {
						board[x][y] = 1;
						blackCount++;
					}
				} else if (x + y * WIDTH >= WIDTH * (WIDTH / 2 + 1)) {
					if ((x + y) % 2 == 1) {
						board[x][y] = 0;
						whiteCount++;
					}
				}
			}
		}

		tileW = handler.getWidth() / WIDTH / 2;
		tileH = handler.getHeight() / WIDTH;

		currentTurn = 0;
	}

	public void tick() {
		if (!gameOver) {
			int lx = handler.getMouseManager().lastClickedX, ly = handler.getMouseManager().lastClickedY;
			int tx = Math.floorDiv(lx, (int) tileW); // tile clicked
			int ty = Math.floorDiv(ly, (int) tileH); // tile clicked
			int tp = getPieceAt(tx, ty); // piece at that tile
			// if (selectedPiece >= 0) {
			if (selectedPiece % 2 == currentTurn && (tp < 0)) { // if a piece of current turn is already selected
																// and
				// tile
				// clicked is empty
				if (possibleMoves.contains(new Tuple<Integer, Integer>(tx, ty))) { // if the tile clicked is movable
					if (selectedPiece == 0 && ty == 0) {
						selectedPiece = 2;
					} else if (selectedPiece == 1 && ty == WIDTH - 1) {
						selectedPiece = 3;
					}
					board[tx][ty] = selectedPiece; // move
					board[selectedX][selectedY] = -1;

					if (Math.abs(tx - selectedX) > 1) { // if captured
						int temp = board[selectedX + (tx - selectedX) / 2][selectedY + (ty - selectedY) / 2];
						
						if (temp % 2 == 0) {
							whiteCount--;
							blackScore++;
						} else {
							blackCount--;
							whiteScore++;
						}
						
						board[selectedX + (tx - selectedX) / 2][selectedY + (ty - selectedY) / 2] = -1;
					} else {
						currentTurn = currentTurn == 0 ? 1 : 0; // invert the turn
					}
				}

				// do everything above
				resetSelection();
			} else {
				if (selectedX != tx || selectedY != ty) {
					selectedX = tx;
					selectedY = ty;
					selectedPiece = tp;
					if (selectedPiece % 2 == currentTurn)
						possibleMoves = Piece.getPossibleMoves(this, selectedX, selectedY); // only calculate moves if
																							// the piece is correct
				}
			}
		}

		if (blackCount <= 0) {
			System.out.println("White win");
			winner = 1;
			gameOver = true;
		} else if (whiteCount <= 0) {
			System.out.println("Black win");
			winner = 0;
			gameOver = true;
		}
	}
	// }

	public void render(Graphics g) {
		for (int y = 0; y < WIDTH; y++) {
			for (int x = 0; x < WIDTH; x++) {
				if ((x + y) % 2 == 1) {
					g.setColor(DARK);
				} else {
					g.setColor(LIGHT);
				}
				g.fillRect((int) (x * tileW), (int) (y * tileH), (int) tileW, (int) tileH);

				int piece = board[x][y];

				if (piece > -1) {
					if (piece % 2 == 0) {
						g.setColor(new Color(200, 200, 200));
					} else if (piece % 2 == 1) {
						g.setColor(Color.BLACK);
					}

					g.fillOval((int) (x * tileW), (int) (y * tileH), (int) tileW, (int) tileH);

					if (piece > 1) {
						g.setColor(Color.yellow);
						g.fillOval((int) (x * tileW) + 5, (int) (y * tileH) + 5, (int) tileW - 10, (int) tileH - 10);
					}
				}
			}
		}

		g.setColor(Color.CYAN);

		if (selectedPiece > -1) {
			if (Math.abs(selectedPiece) % 2 == currentTurn) {
				for (Tuple<Integer, Integer> move : possibleMoves) {
					int x = move.get1();
					int y = move.get2();

					g.fillRect((int) (x * tileW + tileW / 2) - 5, (int) (y * tileH + tileH / 2) - 5, 10, 10);
				}
			}
		}
	}

	public int getPieceAt(int tx, int ty) {
		if (isOutsideBoard(tx) || isOutsideBoard(ty)) {
			return -1;
		}

		return board[tx][ty];
	}

	private void resetSelection() {
		selectedX = -1;
		selectedY = -1;
		selectedPiece = 0;

		possibleMoves.clear();
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public int getCurrentTurn() {
		return currentTurn;
	}

	public void setCurrentTurn(int currentTurn) {
		this.currentTurn = currentTurn;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public int getBlackScore() {
		return blackScore;
	}

	public void setBlackScore(int blackScore) {
		this.blackScore = blackScore;
	}

	public int getWhiteScore() {
		return whiteScore;
	}

	public void setWhiteScore(int whiteScore) {
		this.whiteScore = whiteScore;
	}

}

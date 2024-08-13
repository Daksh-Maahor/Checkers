package checkers.piece;

import java.util.ArrayList;
import java.util.List;

import checkers.board.Board;
import checkers.board.utils.Tuple;

public class Piece {

	private static final int[] OFFSETS = new int[] { 1, -1 };

	public static List<Tuple<Integer, Integer>> getPossibleMoves(Board board, int x, int y) {
		List<Tuple<Integer, Integer>> possibleMoves = new ArrayList<>();

		int thisType = board.getPieceAt(x, y);
		
		if (thisType < 0) {
			return null;
		}

		for (int xOff : OFFSETS) {
			int fx = x + xOff;

			if (Board.isOutsideBoard(fx)) {
				continue;
			}

			for (int yOff : OFFSETS) {
				if ((thisType == 0 && yOff == 1) || (thisType == 1 && yOff == -1)) {
					continue;
				}
				
				int fy = y + yOff;

				if (Board.isOutsideBoard(fy)) {
					continue;
				}

				int p = board.getPieceAt(fx, fy);
				
				if (p != -1) {
					if (p % 2 != thisType % 2) {
						int fx2 = fx + xOff;
						int fy2 = fy + yOff;
						
						int p2 = board.getPieceAt(fx2, fy2);
						
						if (p2 == -1) {
							possibleMoves.add(new Tuple<Integer, Integer>(fx2, fy2));
						}
					}
				} else {
					possibleMoves.add(new Tuple<Integer, Integer>(fx, fy));
				}
			}
		}

		return possibleMoves;
	}

}

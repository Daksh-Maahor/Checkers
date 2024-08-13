package checkers.states;

import java.awt.Color;
import java.awt.Graphics;

import checkers.Handler;
import checkers.gfx.Assets;
import checkers.gfx.Text;

public class ScoreState {
	
	private Handler handler;

	public ScoreState(Handler handler) {
		this.handler = handler;
	}
	
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillRect(handler.getWidth() / 2, 0, handler.getWidth() / 2, handler.getHeight());
		
		if (!handler.getGameState().getBoard().isGameOver()) {
			int turn = handler.getGameState().getBoard().getCurrentTurn();
			
			if (turn % 2 == 0) {
				Text.drawString(g, "White Turn", handler.getWidth() * 2 / 3, handler.getHeight() / 2 - 14, true, Color.WHITE, Assets.font);
			} else {
				Text.drawString(g, "Black Turn", handler.getWidth() * 2 / 3, handler.getHeight() / 2 - 14, true, Color.WHITE, Assets.font);
			}
		} else {
			if (handler.getGameState().getBoard().getWinner() % 2 == 0) {
				Text.drawString(g, "White Won", handler.getWidth() * 2 / 3, handler.getHeight() / 2 - 14, true, Color.WHITE, Assets.font);
			} else {
				Text.drawString(g, "Black Won", handler.getWidth() * 2 / 3, handler.getHeight() / 2 - 14, true, Color.WHITE, Assets.font);
			}
		}
		
		Text.drawString(g, "Black Score : "+handler.getGameState().getBoard().getBlackScore(), handler.getWidth() * 2 / 3 + 20, handler.getHeight() / 2 - 14 + 100, true, Color.WHITE, Assets.font);
		Text.drawString(g, "White Score : "+handler.getGameState().getBoard().getWhiteScore(), handler.getWidth() * 2 / 3 + 20, handler.getHeight() / 2 - 14 - 100, true, Color.WHITE, Assets.font);
	}

}

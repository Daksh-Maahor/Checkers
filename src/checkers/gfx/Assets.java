package checkers.gfx;

import java.awt.Font;

public class Assets {
	
	public static Font font;
	
	public static void init() {
		font = FontLoader.loadFont("res/fonts/slkscr.ttf", 40);
	}

}

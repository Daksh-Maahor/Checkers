package checkers;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import checkers.display.Display;
import checkers.gfx.Assets;
import checkers.input.MouseManager;
import checkers.states.GameState;
import checkers.states.ScoreState;

public class Game implements Runnable {

	private Display display;
	private int width, height;
	public String title;

	private boolean running = false;
	private Thread thread;

	private BufferStrategy bs;
	private Graphics g;

	// Handler
	private Handler handler;
	
	private GameState gameState;
	private ScoreState scoreState;

	private MouseManager mouseManager;

	public Game(String title, int width, int height) {
		this.width = width;
		this.height = height;
		this.title = title;
	}

	private void init() {
		display = new Display(title, width, height);
		
		Assets.init();

		handler = new Handler(this);
		gameState = new GameState(handler);
		scoreState = new ScoreState(handler);

		mouseManager = new MouseManager();

		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
	}

	private void tick() {
		gameState.tick();
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		// Clear Screen
		g.clearRect(0, 0, width, height);
		// Draw Here!
		gameState.render(g);
		scoreState.render(g);
		// End Drawing!
		bs.show();
		g.dispose();
	}

	public void run() {
		init();

		int fps = 60;
		double timePerTick = 1000000000 / fps;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
//		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
//				ticks++;
				delta--;
			}

			if (timer >= 1000000000) {
				// System.out.println("Ticks and Frames: " + ticks);
//				ticks = 0;
				timer = 0;
			}
		}

		stop();

	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

}

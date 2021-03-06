/*** In The Name of Allah ***/
package bufferstrategy;

/**
 * A very simple structure for the main game loop.
 * THIS IS NOT PERFECT, but works for most situations.
 * Note that to make this work, none of the 2 methods 
 * in the while loop (update() and render()) should be 
 * long running! Both must execute very quickly, without 
 * any waiting and blocking!
 * 
 * Detailed discussion on different game loop design
 * patterns is available in the following link:
 *    http://gameprogrammingpatterns.com/game-loop.html
 * 
 * @author base code: Seyed Mohammad Ghaffarian | Modified by Arash Hajisafi
 */
public class GameLoop implements Runnable {

	/**
	 * Frame Per Second.
	 * Higher is better, but any value above 24 is fine.
	 */
	public static final int FPS = 60;

	private GameFrame canvas;
	private static GameState state;

	public GameLoop(GameFrame frame) {
		canvas = frame;
	}

	/**
	 * This must be called before the game loop starts.
	 */
	public void init() {
		state = new GameState();
		canvas.addKeyListener(state.getKeyListener());
		canvas.addMouseListener(state.getMouseListener());
		canvas.addMouseMotionListener(state.getMouseMotionListener());
	}

	@Override
	public void run() {
		int gameOver = 0;
		while (gameOver == 0) {
				try {
					long start = System.currentTimeMillis();
					//
					state.update();
					canvas.render(state);
					gameOver = state.gameOver;
					//
					long delay = (1000 / FPS) - (System.currentTimeMillis() - start);
					if (delay > 0)
						Thread.sleep(delay);
				} catch (InterruptedException ex) {
			}
		}
		canvas.render(state);
	}

	public static GameState getState() {
		return state;
	}

	public static void setState(GameState state) {
		GameLoop.state = state;
	}
}

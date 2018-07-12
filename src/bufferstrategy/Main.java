/*** In The Name of Allah ***/
package bufferstrategy;

import gameMap.Tile;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Program start.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class Main {
	public static int gameMode; //0: Single Player | 1: Multiplayer
	public static int gameDifficulty = 0; //1: Easy | 2: Medium | 3: Hard
	public static int connectionType = -1; //0: Host | 1: Client | -1: Single Player
	
    public static void main(String[] args) {
		// Initialize the global thread-pool
		ThreadPool.init();
		Thread startMenu = new Thread(new StartMenu(1280 , 720));
		startMenu.run();

		// Show the game menu ...
		
		// After the player clicks 'PLAY' ...
		EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				try {
					startMenu.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Difficulty: " + gameDifficulty);
				System.out.println("Mode: " + gameMode);
				System.out.println("Connection type: " + connectionType);
				new Tile(); //load Images
				GameFrame frame = new GameFrame("JTanks");
				frame.setLocationRelativeTo(null); // put frame at center of screen
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setVisible(true);
				frame.initBufferStrategy();
				// Create and execute the game-loop
				GameLoop game = new GameLoop(frame);
				game.init();
				ThreadPool.execute(game);
				// and the game starts ...
			}
		});
    }
}

/*** In The Name of Allah ***/
package bufferstrategy;

import gameMap.Tile;
import multiplayer.GameClient;
import multiplayer.GameServer;
import multiplayer.OtherTank;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 * Program start.
 * 
 * @author Arash Hajisafi
 */
public class Main {
	public static int gameMode; //0: Single Player | 1: Multiplayer
	public static int gameDifficulty = 0; //1: Easy | 2: Medium | 3: Hard
	public static int connectionType = -1; //0: Host | 1: Client | -1: Single Player
	public static GameServer gameServer;
	public static GameClient gameClient;

	public static OtherTank otherTank; //Other player for MP play

    public static void main(String[] args) {
		new Tile(); //load Images

		// Initialize the global thread-pool
		ThreadPool.init();
		Thread startMenu = new Thread(new StartMenu(1280, 720));
		startMenu.run();

		Thread gameConnection = null;

		if(gameMode == 1){
			if(connectionType == 0){
				gameServer = new GameServer();
				gameConnection = new Thread(gameServer);
				gameConnection.start();
			}
			else if(connectionType == 1){
				gameClient = new GameClient();
				gameConnection = new Thread(gameClient);
				gameConnection.start();
			}
		}

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
			//	new Tile(); //load Images
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

/*** In The Name of Allah ***/
package bufferstrategy;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import gameMap.Tile;
import javafx.scene.transform.Rotate;
import multiplayer.GameClient;
import multiplayer.GameServer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * The window on which the rendering is performed.
 * This example uses the modern BufferStrategy approach for double-buffering, 
 * actually it performs triple-buffering!
 * For more information on BufferStrategy check out:
 *    http://docs.oracle.com/javase/tutorial/extra/fullscreen/bufferstrategy.html
 *    http://docs.oracle.com/javase/8/docs/api/java/awt/image/BufferStrategy.html
 * 
 * @author Base sample: Seyed Mohammad Ghaffarian / Heavily modified by: Arash Hajisafi
 */
public class GameFrame extends JFrame {
	
	public static final int GAME_HEIGHT = 720;                  // 720p game resolution
	public static final int GAME_WIDTH = 16 * GAME_HEIGHT / 9;  // wide aspect ratio

/*	private BufferedImage base;
    private BufferedImage turret1;
    private BufferedImage turret2; */

    private long lastRender;
	private ArrayList<Float> fpsHistory;

	private BufferStrategy bufferStrategy;
	
	public GameFrame(String title) {
		super(title);
		setResizable(false);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		lastRender = -1;
		fpsHistory = new ArrayList<>(100);

		if(Main.connectionType == 1){
			Main.otherTank = GameClient.otherTank;
		}
		else if(Main.connectionType == 0){
			Main.otherTank = GameServer.otherTank;
		}

	/*	try{
			base = ImageIO.read(new File("res\\tank.png"));
			turret1 = ImageIO.read(new File("res\\tankGun01.png"));
			turret2 = ImageIO.read(new File("res\\tankGun2.png"));

        }
		catch(IOException e){
			System.out.println(e);
		} */
	}
	
	/**
	 * This must be called once after the JFrame is shown:
	 *    frame.setVisible(true);
	 * and before any rendering is started.
	 */
	public void initBufferStrategy() {
		// Triple-buffering
		createBufferStrategy(3);
		bufferStrategy = getBufferStrategy();
	}

	
	/**
	 * Game rendering with triple-buffering using BufferStrategy.
	 */
	public void render(GameState state) {
		// Render single frame
		do {
			// The following loop ensures that the contents of the drawing buffer
			// are consistent in case the underlying surface was recreated
			do {
				// Get a new graphics context every time through the loop
				// to make sure the strategy is validated
				Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
				try {
					doRendering(graphics, state);
				} finally {
					// Dispose the graphics
					graphics.dispose();
				}
				// Repeat the rendering if the drawing buffer contents were restored
			} while (bufferStrategy.contentsRestored());

			// Display the buffer
			bufferStrategy.show();
			// Tell the system to do the drawing NOW;
			// otherwise it can take a few extra ms and will feel jerky!
			Toolkit.getDefaultToolkit().sync();

		// Repeat the rendering if the drawing buffer was lost
		} while (bufferStrategy.contentsLost());
	}
	
	/**
	 * Rendering all game elements based on the game state.
	 */
	private void doRendering(Graphics2D g2d, GameState state) {
		// Draw background
	//	g2d.setColor(new Color(235,244,250));
		if((!state.isPaused() || Main.gameMode == 1) && !GameState.isServerPause()) {
			g2d.setColor(new Color(190, 214, 220));

			g2d.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);

			state.level.tick(state);
			state.tank.tick();
			state.enemies.tick(state);

			state.level.render(g2d, state);
			state.tank.render(g2d, state);
			if (Main.gameMode == 1)
				Main.otherTank.render(g2d, state);
			state.enemies.render(g2d, state);
			state.loots.render(g2d, state);
		}



	/*	g2d.setColor(Color.BLACK);
		g2d.fillOval(state.locX, state.locY, state.diam, state.diam);
		g2d.drawImage(base,state.locX,state.locY,null);
		AffineTransform backupAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        if (state.getTargetPoint() != null) {
            g2d.draw(new Line2D.Double(state.getCenter().x + 33 , state.getCenter().y + 30 , state.getTargetPoint().x, state.getTargetPoint().y));
            double deltaX = - ( state.getCenter().x + 33) + state.getTargetPoint().x ;
            double deltaY = - ( state.getCenter().y + 30) + state.getTargetPoint().y;
            double rotation = Math.atan2(deltaY, deltaX);
            at.rotate(rotation, state.getCenter().x + 33 , state.getCenter().y + 30);
        }
        g2d.setTransform(at);
        if(state.getGunState() == 0) {
            g2d.drawImage(turret1 , state.getCenter().x, state.getCenter().y, null);
        }
        else {
            g2d.drawImage(turret2 , state.getCenter().x , state.getCenter().y , null);
        }
		g2d.setTransform(backupAt); */



		// Print FPS info
		long currentRender = System.currentTimeMillis();
		if (lastRender > 0) {
			fpsHistory.add(1000.0f / (currentRender - lastRender));
			if (fpsHistory.size() > 100) {
				fpsHistory.remove(0); // remove oldest
			}
			float avg = 0.0f;
			for (float fps : fpsHistory) {
				avg += fps;
			}
			avg /= fpsHistory.size();
			String str = String.format("Average FPS = %.1f , Last Interval = %d ms",
					avg, (currentRender - lastRender));
			g2d.setColor(Color.CYAN);
			g2d.setFont(g2d.getFont().deriveFont(18.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			int strHeight = g2d.getFontMetrics().getHeight();
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, strHeight+50);
		}
		lastRender = currentRender;
		// Print user guide
/*		String userGuide
				= "Use the MOUSE or ARROW KEYS to move the BALL. "
				+ "Press ESCAPE to end the game."; */
		String userGuide = "Last Event: " + GameState.lastEvent +  " Number of remaining Enemies: " + GameState.enemies.getMovingSprites().size();;
		g2d.setFont(g2d.getFont().deriveFont(18.0f));
		g2d.drawString(userGuide, 10, GAME_HEIGHT - 10);
		g2d.drawString("" + GameState.tank.getNumOfBullet() , 60 , 80);
		g2d.drawString("" + GameState.tank.getNumOfMissiles() , 170 , 80);
		g2d.setFont(g2d.getFont().deriveFont(25.0f));
		g2d.drawString("Tank HP: " + GameState.tank.getHp() , 10 , 113);

		g2d.drawImage(Tile.numberOfLightBullets , 10,25,null);
		g2d.drawImage(Tile.numberOfHeavyBullets , 120,36,null);
		// Draw GAME OVER
		if (state.gameOver == 1) {
			String str = "GAME OVER";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
		} // Winning a game
		else if (state.gameOver == 2) {
			String str = "You win the game!";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
		} // Game Paused
		else if(state.isPaused() && Main.gameMode == 0){
			String str = "Paused";
			g2d.setColor(Color.WHITE);
			g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
			int strWidth = g2d.getFontMetrics().stringWidth(str);
			g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
		} // Server Paused
        else if(GameState.isServerPause()){
            String str = "Other player Left, Waiting for Reconnect...";
            g2d.setColor(Color.WHITE);
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD).deriveFont(64.0f));
            int strWidth = g2d.getFontMetrics().stringWidth(str);
            g2d.drawString(str, (GAME_WIDTH - strWidth) / 2, GAME_HEIGHT / 2);
        }
	}
	
}

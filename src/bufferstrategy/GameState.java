/*** In The Name of Allah ***/
package bufferstrategy;

import gameMap.Level;
import gameMap.Tank;
import gameMap.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 * 
 * @author Seyed Mohammad Ghaffarian
 */
public class GameState {
	
/*	public int locX, locY, tankHeight, tankWidth, diam; */
	public boolean gameOver;
	public static Level level;
	public static Tank tank;
	public static int sX = 0 , sY = 0;
	
	public static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
	private boolean mousePress;
	private int mouseX, mouseY;	
	private KeyHandler keyHandler;
	private MouseHandler mouseHandler;
	private Point targetPoint;
	private Point previousPoint;
	private double degree;
	private int gunState;
	private int mouseStatus = 0;
	private int view = 0;
	
	public GameState() {
		level = new Level();
		tank = new Tank();
		gameOver = false;
		//
		keyUP = false;
		keyDOWN = false;
		keyRIGHT = false;
		keyLEFT = false;
		//
		mousePress = false;
		mouseX = 0;
		mouseY = 0;
		//
		keyHandler = new KeyHandler();
		mouseHandler = new MouseHandler();
		gunState = 0;
	}
	
	/**
	 * The method which updates the game state.
	 */
	public void update() {
	/*	if (mousePress) {
			locY = mouseY - diam / 2;
			locX = mouseX - diam / 2;

		}
		if (keyUP) {
			tank.locY -= 3;
			sY -= 3;
		}
		if (keyDOWN) {
			tank.locY += 3;
			sY += 3;
		}
		if (keyLEFT) {
			tank.locX -= 3;
			sX -= 3;
		}
		if (keyRIGHT) {
			tank.locX += 3;
			sX += 3;
		}

		tank.locX = Math.max(tank.locX, 0);
		tank.locX = Math.min(tank.locX, GameFrame.GAME_WIDTH - tank.tankHeight);
		tank.locY = Math.max(tank.locY, 27);
		tank.locY = Math.min(tank.locY, GameFrame.GAME_HEIGHT - tank.tankWidth); */
	}
	
	
	public KeyListener getKeyListener() {
		return keyHandler;
	}
	public MouseListener getMouseListener() {
		return mouseHandler;
	}
	public MouseMotionListener getMouseMotionListener() {
		return mouseHandler;
	}



	/**
	 * The keyboard handler.
	 */
	class KeyHandler extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			if (!tank.isColliding(new Point((int) tank.x + sX , (int) tank.y + sY), new Point((int) (tank.x + tank.width) + sX , (int) (tank.y + tank.height + sY)))) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
						keyUP = true;
						break;
					case KeyEvent.VK_DOWN:
						keyDOWN = true;
						break;
					case KeyEvent.VK_LEFT:
						keyLEFT = true;
						break;
					case KeyEvent.VK_RIGHT:
						keyRIGHT = true;
						break;
					case KeyEvent.VK_ESCAPE:
						gameOver = true;
						break;
				}
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode())
			{
				case KeyEvent.VK_UP:
					keyUP = false;
					break;
				case KeyEvent.VK_DOWN:
					keyDOWN = false;
					break;
				case KeyEvent.VK_LEFT:
					keyLEFT = false;
					break;
				case KeyEvent.VK_RIGHT:
					keyRIGHT = false;
					break;
			}
		}

	}

	/**
	 * The mouse handler.
	 */
	class MouseHandler extends MouseAdapter {

		@Override
		public void mousePressed(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
			mousePress = true;
			if(SwingUtilities.isRightMouseButton(e))
			    gunState = ++gunState%2 ;
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			mousePress = false;
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseX = e.getX();
			mouseY = e.getY();
		}

        @Override
        public void mouseMoved(MouseEvent e) {
		    targetPoint = e.getPoint();
		    mouseStatus = ++mouseStatus % 10;
		    if(mouseStatus == 0)
		    	previousPoint=targetPoint;
	/*	    if(previousPoint != null) {
				if (targetPoint.x - previousPoint.x < 0 && tank.locX+tank.tankWidth < GameFrame.GAME_WIDTH){
					tank.locX += 5;
					sX -= 5;
			}
				else if (targetPoint.x - previousPoint.x > 0 && tank.locX > 0){
					tank.locX -= 5;
					sX += 5;
				}
				else if (targetPoint.y - previousPoint.y > 0 && tank.locY-tank.tankHeight/2 > 0){
					tank.locY -= 5;
					sY += 5;
				}
				else if (targetPoint.y - previousPoint.y < 0 && tank.locY + tank.tankHeight < GameFrame.GAME_HEIGHT){
					tank.locY += 5;
					sY -= 5;
				}

		    } */
        }
    }

    public Point getCenter(){
	    Point point = new Point(tank.locX + tank.diam / 2 - sX , tank.locY + tank.diam / 2 - sY );
	    return point;
    }

    public Point getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(Point targetPoint) {
        this.targetPoint = targetPoint;
    }

	public int getGunState() {
		return gunState;
	}

	public void setGunState(int gunState) {
		this.gunState = gunState;
	}
}


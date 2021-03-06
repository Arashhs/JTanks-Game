/*** In The Name of Allah ***/
package bufferstrategy;

import gameMap.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * This class holds the state of game and all of its elements.
 * This class also handles user inputs, which affect the game state.
 *
 * @author Arash Hajisafi
 */
public class GameState implements Serializable {

    public static Level level;
    public static Tank tank;
    public static int sX = 0, sY = 0;
    public static Enemies enemies;
    public static Loot loots;
    public static String lastEvent;
    public static boolean keyUP, keyDOWN, keyRIGHT, keyLEFT;
    private static Point targetPoint;
    /*	public int locX, locY, tankHeight, tankWidth, diam; */
    public int gameOver; //0: Playing | 1: Game over | 2: Win
    private boolean mousePress;
    private int mouseX, mouseY;
    private KeyHandler keyHandler;
    private MouseHandler mouseHandler;
    private Point previousPoint;
    private double degree;
    private int gunState;
    private int mouseStatus = 0;
    private int view = 0;
    private int fireConst;
    private Tank tankForNet;
    private Enemies enemiesForNet;
    private Level levelForNet;
    private boolean paused;
    private static boolean serverPause;

    public GameState() {
        tank = new Tank();
        gameOver = 0;
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
        fireConst = 0;
        enemies = new Enemies(1);
        loots = new Loot();
        level = new Level();
        lastEvent = "";
        paused = false;
        serverPause = false;
    }

    /* *********************Getter and setters ***************** */
    public static Point getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(Point targetPoint) {
        this.targetPoint = targetPoint;
    }

    public static Tank getTank() {
        return tank;
    }

    public static Level getLevel() {
        return level;
    }

    public static void setLevel(Level level) {
        GameState.level = level;
    }

    public static Enemies getEnemies() {
        return enemies;
    }

    public static void setEnemies(Enemies enemies) {
        GameState.enemies = enemies;
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

    public Point getCenter() {
        Point point = new Point(tank.locX + tank.diam / 2 - sX, tank.locY + tank.diam / 2 - sY);
        return point;
    }

    public int getGunState() {
        return gunState;
    }

    public void setGunState(int gunState) {
        this.gunState = gunState;
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    /* *********************Getter and setters ***************** */

    /**
     * Checking whether or not machineGun is constantly firing
     */
    public void checkFireConstant() {
        fireConst++;
        BulletSprite bullet = null;
        if (mousePress && fireConst % 8 == 0 && gunState == 1) {
            double tetae = Math.atan2(GameState.getTargetPoint().y + GameState.sY - tank.locY, GameState.getTargetPoint().x + GameState.sX - tank.locX);
            if (tank.getNumOfBullet() > 0) {
                bullet = new LightBullet(new Rectangle(tank.locX, tank.locY, tank.width, tank.height), tetae);
                bullet.setSource(-1);
                tank.getBulletSprites().add(bullet);
                bullet.setDamage(Tank.tankBulletDamage);
                bullet.shoot();
                tank.decreaseLightBullet();
            }

        }
    }

    /* *********************Getter and setters ***************** */

    public int getGameOver() {
        return gameOver;
    }

    public void setGameOver(int gameOver) {
        this.gameOver = gameOver;
    }

    public Tank getTankForNet() {
        return tankForNet;
    }

    public void setTankForNet(Tank tankForNet) {
        this.tankForNet = tankForNet;
    }

    public Enemies getEnemiesForNet() {
        return enemiesForNet;
    }

    public void setEnemiesForNet(Enemies enemiesForNet) {
        this.enemiesForNet = enemiesForNet;
    }

    public Level getLevelForNet() {
        return levelForNet;
    }

    public void setLevelForNet(Level levelForNet) {
        this.levelForNet = levelForNet;
    }
    /* *********************Getter and setters ***************** */


    /**
     * Custom serialization for this class. Is used for network and save/load
     * @param stream Output stream
     * @throws IOException
     */
    private void writeObject(java.io.ObjectOutputStream stream)
            throws IOException {
        stream.writeObject(tank);
    //    stream.writeObject(level);
        stream.writeObject(enemies);
    }

    /**
     * Custom serialization for this class. Is used for network and save/load
     * @param stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(java.io.ObjectInputStream stream)
            throws IOException, ClassNotFoundException {
        tankForNet = (Tank) stream.readObject();
    //    levelForNet = (Level) stream.readObject();
        enemiesForNet = (Enemies) stream.readObject();
    }

    /**
     * The keyboard handler.
     */
    class KeyHandler extends KeyAdapter implements Serializable {

        @Override
        public void keyPressed(KeyEvent e) {
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
                    paused = !paused;
                    break;
                //		}
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
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
    class MouseHandler extends MouseAdapter implements Serializable {

        @Override
        public synchronized void mousePressed(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
            mousePress = true;
            double teta = Math.atan2(GameState.getTargetPoint().y + GameState.sY - tank.locY, GameState.getTargetPoint().x + GameState.sX - tank.locX);
            if (SwingUtilities.isRightMouseButton(e))
                gunState = ++gunState % 2;
            if (SwingUtilities.isLeftMouseButton(e)) {
                BulletSprite bullet = null;
                switch (gunState) {
                    case 0:
                        if (tank.getNumOfMissiles() > 0) {
                            bullet = new Missile(new Rectangle(tank.locX, tank.locY, tank.width, tank.height), teta);
                            bullet.setDamage(Tank.tankMissileDamage);
                            tank.decreaseMissile();
                            bullet.setSource(-1);
                            tank.getBulletSprites().add(bullet);
                            bullet.shoot();
                        }
                        break;
                    case 1:
                        if (tank.getNumOfBullet() > 0) {
                            bullet = new LightBullet(new Rectangle(tank.locX, tank.locY, tank.width, tank.height), teta);
                            bullet.setDamage(Tank.tankBulletDamage);
                            tank.decreaseLightBullet();
                            bullet.setSource(-1);
                            tank.getBulletSprites().add(bullet);
                            bullet.shoot();
                        }
                        break;

                }
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mousePress = false;
        }

        public void mouseDragged(MouseEvent e) {
            targetPoint = e.getPoint();
            mouseStatus = ++mouseStatus % 10;
            if (mouseStatus == 0)
                previousPoint = targetPoint;
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            targetPoint = e.getPoint();
            mouseStatus = ++mouseStatus % 10;
            if (mouseStatus == 0)
                previousPoint = targetPoint;
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

    /* *********************Getter and setters ***************** */
    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public static boolean isServerPause() {
        return serverPause;
    }

    public static void setServerPause(boolean serverPause) {
        GameState.serverPause = serverPause;
    }
    /* *********************Getter and setters ***************** */

}


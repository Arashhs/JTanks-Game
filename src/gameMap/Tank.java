package gameMap;

import bufferstrategy.GameFrame;
import bufferstrategy.GameState;
import bufferstrategy.Main;
import multiplayer.GameClient;
import multiplayer.GameServer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tank extends MovingSprite implements Serializable {
    public int locX, locY, tankHeight, tankWidth, diam;

    public static int tankMissileDamage, tankBulletDamage;

    public static final int MAX_HP = 1200;

  //  private transient BufferedImage image;

    private CopyOnWriteArrayList<BulletSprite> bulletSprites;

    private int numOfBullet, numOfMissiles;

    protected int gunState;

    private static transient BufferedImage turret1;
    private static transient BufferedImage turret2;

    protected boolean isVertical;
    protected boolean cannonUpgraded;
    protected boolean machinGunUpgraded;

    public Tank() {
        super(500, 300, 100, 100, Tile.base, Tile.base2, -1);
        this.locX = 500;
        this.locY = 300;
        tankHeight = 100;
        tankWidth = 100;
        diam = 32;
        setBounds(locX, locY, tankHeight, tankWidth);
        bulletSprites = new CopyOnWriteArrayList<BulletSprite>();
        image = Tile.base;
        hp = 1200;
        tankMissileDamage = 100;
        tankBulletDamage = 40;
        baseImage = Tile.base;
        turret1 = Tile.turret1;
        turret2 = Tile.turret2;
        numOfBullet = 300;
        numOfMissiles = 50;
        isVertical = false;
        cannonUpgraded = false;
        machinGunUpgraded = false;
    }

    public synchronized void  tick() {
        if (hp <= 0) {
        }
        {
            if (GameState.keyUP) {
                if (!isColliding(new Rectangle(x + GameState.sX, y - 7 + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locY -= 5;
                    GameState.sY -= 5;
                    image = Tile.base2;
                    isVertical = true;

                }
            }
            if (GameState.keyDOWN) {
                if (!isColliding(new Rectangle(x + GameState.sX, y + 7 + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locY += 5;
                    GameState.sY += 5;
                    image = Tile.base2;
                    isVertical = true;
                }
            }
            if (GameState.keyLEFT) {
                if (!isColliding(new Rectangle(x - 7 + GameState.sX, y + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locX -= 5;
                    GameState.sX -= 5;
                    image = Tile.base;
                    isVertical = false;
                }

            }
            if (GameState.keyRIGHT) {
                if (!isColliding(new Rectangle(x + 7 + GameState.sX, y + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locX += 5;
                    GameState.sX += 5;
                    image = Tile.base;
                    isVertical = false;
                }
            }
        }
    }

/*    public boolean isColliding(Point p1 , Point p2){
        for(int i = (int) ( (x + GameState.sX) / Tile.tileSize); i < (int) ( (x + GameState.sX + GameFrame.GAME_WIDTH) / Tile.tileSize ) ; i++) {
            for (int j = (int) ( (y + GameState.sY) / Tile.tileSize); j < (int) ( (y + GameState.sY + GameFrame.GAME_HEIGHT)  / Tile.tileSize ); j++) {
                if((i==0 && j==0) || (i==7 && j==7)){
                    System.out.println("P1: " + p1 + "\nP2: "+p2 + "\nX: " + GameState.level.blocks[i][j].x + "\nY: " + GameState.level.blocks[i][j].y);
                }
                if((GameState.level.blocks[i][j].isCollidable()) && (GameState.level.blocks[i][j].contains(p1) || GameState.level.blocks[i][j].contains(p2)))
                    return true;

            }
        }
        return false;
    } */

    public synchronized boolean isColliding(Rectangle rectangle) {
        if(Main.connectionType == 0){
            if(rectangle.intersects(new Rectangle(GameServer.otherTank.locX , GameServer.otherTank.locY , tankWidth , tankHeight)))
                return true;
        }
        else if(Main.connectionType == 1){
            if(rectangle.intersects(new Rectangle(GameClient.otherTank.locX , GameClient.otherTank.locY , tankWidth , tankHeight))) {
                return true;
            }
        }
        for (MovingSprite ms : GameState.enemies.getMovingSprites()) {
            if (rectangle.intersects(ms)) {
                ms.collideWithTank = true;
                return true;
            }
        }
        for (int i = Math.max((int) ((x + GameState.sX) / Tile.tileSize) - 1, 0); i < Math.min((int) ((x + GameState.sX + width) / Tile.tileSize) + 2, GameState.level.blocks.length); i++) {
            for (int j = Math.max((int) ((y + GameState.sY) / Tile.tileSize) - 1, 0); j < Math.min((int) ((y + GameState.sY + height) / Tile.tileSize) + 2, GameState.level.blocks[i].length); j++) {
                if (GameState.level.blocks[i][j].isCollidable() && rectangle.intersects(GameState.level.blocks[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }


    public synchronized void render(Graphics2D g2d, GameState state) {
        gunState = state.getGunState();
        g2d.drawImage(image, locX - state.sX, locY - state.sY, null);
        AffineTransform backupAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        if (state.getTargetPoint() != null) {
            g2d.draw(new Line2D.Double(state.getCenter().x + 33, state.getCenter().y + 30, state.getTargetPoint().x, state.getTargetPoint().y));
            double deltaX = -(state.getCenter().x + 33) + state.getTargetPoint().x;
            double deltaY = -(state.getCenter().y + 30) + state.getTargetPoint().y;
            turretAngle = Math.atan2(deltaY, deltaX);
            at.rotate(turretAngle, state.getCenter().x + 33, state.getCenter().y + 30);
        }
        g2d.setTransform(at);
        if (state.getGunState() == 0) {
            g2d.drawImage(turret1, state.getCenter().x, state.getCenter().y, null);
        } else {
            g2d.drawImage(turret2, state.getCenter().x, state.getCenter().y, null);
        }
        g2d.setTransform(backupAt);
        state.checkFireConstant();
        for (int i = 0; i < bulletSprites.size(); i++) {
            if (bulletSprites.get(i).isCollided()) {
                bulletSprites.remove(i);
                continue;
            }
            bulletSprites.get(i).move(g2d);
        }
    }

    public CopyOnWriteArrayList<BulletSprite> getBulletSprites() {
        return bulletSprites;
    }

    public static BufferedImage getTurret1() {
        return turret1;
    }

    public static void setTurret1(BufferedImage turret1) {
        Tank.turret1 = turret1;
    }

    public static BufferedImage getTurret2() {
        return turret2;
    }

    public static void setTurret2(BufferedImage turret2) {
        Tank.turret2 = turret2;
    }

    public int getNumOfBullet() {
        return numOfBullet;
    }

    public void setNumOfBullet(int numOfBullet) {
        this.numOfBullet = numOfBullet;
    }

    public int getNumOfMissiles() {
        return numOfMissiles;
    }

    public void setNumOfMissiles(int numOfMissiles) {
        this.numOfMissiles = numOfMissiles;
    }

    public void decreaseLightBullet(){
        numOfBullet--;
    }

    public void decreaseMissile(){
        numOfMissiles--;
    }

    public void increaseMissile(){
        numOfMissiles += 20;
    }

    public void increaseMachinGunAmmo(){
        numOfBullet += 50;
    }

    @Override
    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setBulletSprites(CopyOnWriteArrayList<BulletSprite> bulletSprites) {
        this.bulletSprites = bulletSprites;
    }

    public boolean isVertical() {
        return isVertical;
    }

    public void setVertical(boolean vertical) {
        isVertical = vertical;
    }

    public int getGunState() {
        return gunState;
    }

    public void setGunState(int gunState) {
        this.gunState = gunState;
    }

    public boolean isCannonUpgraded() {
        return cannonUpgraded;
    }

    public void setCannonUpgraded(boolean cannonUpgraded) {
        this.cannonUpgraded = cannonUpgraded;
    }

    public boolean isMachinGunUpgraded() {
        return machinGunUpgraded;
    }

    public void setMachinGunUpgraded(boolean machinGunUpgraded) {
        this.machinGunUpgraded = machinGunUpgraded;
    }
}





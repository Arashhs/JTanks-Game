package gameMap;

import bufferstrategy.GameFrame;
import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Tank extends MovingSprite {
    public int locX, locY, tankHeight, tankWidth, diam;

    private BufferedImage image;

    private ArrayList<BulletSprite> bulletSprites;

    public Tank() {
        super(500 , 300 , 100 , 100 , Tile.base , Tile.base2 , -1);
        locX = 500;
        locY = 300;
        tankHeight = 100;
        tankWidth = 100;
        diam = 32;
        setBounds(locX, locY, tankHeight, tankWidth);
        bulletSprites = new ArrayList<BulletSprite>();
        image = Tile.base;
        hp = 1000;
    }

    public void tick() {
        if(hp <= 0){
        }
        {
            if (GameState.keyUP) {
                if (!isColliding(new Rectangle(x + GameState.sX, y - 7 + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locY -= 5;
                    GameState.sY -= 5;
                    image = Tile.base2;

                }
            }
            if (GameState.keyDOWN) {
                if (!isColliding(new Rectangle(x + GameState.sX, y + 7 + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locY += 5;
                    GameState.sY += 5;
                    image = Tile.base2;
                }
            }
            if (GameState.keyLEFT) {
                if (!isColliding(new Rectangle(x - 7 + GameState.sX, y + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locX -= 5;
                    GameState.sX -= 5;
                    image = Tile.base;
                }

            }
            if (GameState.keyRIGHT) {
                if (!isColliding(new Rectangle(x + 7 + GameState.sX, y + GameState.sY, tankWidth, tankHeight))) {
                    GameState.tank.locX += 5;
                    GameState.sX += 5;
                    image = Tile.base;
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

    public boolean isColliding(Rectangle rectangle) {
        for(MovingSprite ms : GameState.enemies.getMovingSprites()){
            if(rectangle.intersects(ms)) {
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


    public void render(Graphics2D g2d, GameState state) {

        g2d.drawImage(image, locX - state.sX, locY - state.sY, null);
        AffineTransform backupAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        if (state.getTargetPoint() != null) {
            g2d.draw(new Line2D.Double(state.getCenter().x + 33, state.getCenter().y + 30, state.getTargetPoint().x, state.getTargetPoint().y));
            double deltaX = -(state.getCenter().x + 33) + state.getTargetPoint().x;
            double deltaY = -(state.getCenter().y + 30) + state.getTargetPoint().y;
            double rotation = Math.atan2(deltaY, deltaX);
            at.rotate(rotation, state.getCenter().x + 33, state.getCenter().y + 30);
        }
        g2d.setTransform(at);
        if (state.getGunState() == 0) {
            g2d.drawImage(Tile.turret1, state.getCenter().x, state.getCenter().y, null);
        } else {
            g2d.drawImage(Tile.turret2, state.getCenter().x, state.getCenter().y, null);
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

    public ArrayList<BulletSprite> getBulletSprites() {
        return bulletSprites;
    }


}

package gameMap;

import bufferstrategy.GameFrame;
import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;

public class Tank extends Rectangle {
    public int locX, locY, tankHeight, tankWidth, diam;

    public Tank() {
        locX = 500;
        locY = 300;
        tankHeight = 100;
        tankWidth = 100;
        diam = 32;
        setBounds(locX, locY, tankHeight, tankWidth);
    }

    public void tick() {
         {
            if (GameState.keyUP) {
                GameState.tank.locY -= 5;
                GameState.sY -= 5;
            }
            if (GameState.keyDOWN) {
                GameState.tank.locY += 5;
                GameState.sY += 5;
            }
            if (GameState.keyLEFT) {
                GameState.tank.locX -= 5;
                GameState.sX -= 5;
            }
            if (GameState.keyRIGHT) {
                GameState.tank.locX += 5;
                GameState.sX += 5;
            }
        }
    }

    public boolean isColliding(Point p1 , Point p2){
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
    }



    public void render(Graphics2D g2d, GameState state) {

        g2d.drawImage(Tile.base , locX - state.sX , locY - state.sY ,  null);
        AffineTransform backupAt = g2d.getTransform();
        AffineTransform at = new AffineTransform();
        if (state.getTargetPoint() != null) {
            g2d.draw(new Line2D.Double(state.getCenter().x + 33, state.getCenter().y + 30, state.getTargetPoint().x, state.getTargetPoint().y));
            double deltaX = -(state.getCenter().x + 33) + state.getTargetPoint().x;
            double deltaY = -(state.getCenter().y + 30) + state.getTargetPoint().y;
            double rotation = Math.atan2(deltaY, deltaX);
            at.rotate(rotation, state.getCenter().x + 33, state.getCenter().y + 30 );
        }
        g2d.setTransform(at);
        if (state.getGunState() == 0) {
            g2d.drawImage(Tile.turret1, state.getCenter().x, state.getCenter().y , null);
        } else {
            g2d.drawImage(Tile.turret2, state.getCenter().x, state.getCenter().y  , null);
        }
        g2d.setTransform(backupAt);
    }
}

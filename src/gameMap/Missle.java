package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Missle extends Sprite {


    public static final double missleSpeed = 10;


    private boolean collided, shooting;

    public Missle() {
        super((int) (GameState.tank.getX()+33) , (int)(GameState.tank.getY() + 33) , 23 , 9 );
        image = Tile.missle;
        collided = false;
        shooting = false;
        dx = 0;
        dy = 0;
    }

    public void shoot(){
        shooting = true;
        angle = Math.atan2(GameState.getTargetPoint().y - y,  GameState.getTargetPoint().x - x);
        dx = (missleSpeed) * Math.cos(angle);
        dy = (missleSpeed) * Math.sin(angle);
    }

    public void move(Graphics2D g){
        x += dx;
        y += dy;
        AffineTransform backupAt = g.getTransform();
        AffineTransform at = new AffineTransform();
        at.rotate(angle, x, y );
        g.setTransform(at);
        g.drawImage(image , x , y,null);
        g.setTransform(backupAt);
    }

    public boolean isCollided() {
        return collided;
    }

    public void setCollided(boolean collided) {
        this.collided = collided;
    }

    public boolean isShooting() {
        return shooting;
    }

    public void setShooting(boolean shooting) {
        this.shooting = shooting;
    }
}

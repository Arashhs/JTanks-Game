package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Missile extends Sprite {


    public static final double missleSpeed = 10;


    private boolean collided, shooting;

    public Missile(Tank source) {
        super((int) (source.locX + 33) , (int)(source.locY + 33) , 23 , 9 );
        image = Tile.missle;
        collided = false;
        shooting = false;
        dx = 0;
        dy = 0;
    }

    public void shoot(){
        shooting = true;
        state = 0;
        angle = Math.atan2(GameState.getTargetPoint().y + GameState.sY - y,  GameState.getTargetPoint().x + GameState.sX - x);
        dx = (missleSpeed) * Math.cos(angle);
        dy = (missleSpeed) * Math.sin(angle);

    }

    public void move(Graphics2D g){
        if(isBulletColliding(new Rectangle(x,y,width,height))){
            collided = true;
            return;
        }
        x += dx;
        y += dy;
        drawRotated(angle , x - GameState.sX , y - GameState.sY , g);
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

package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BulletSprite extends Sprite {
    protected int bulletSpeed;
    protected int damage;

    private boolean collided, shooting;

    public BulletSprite( int x , int y , int width , int height , BufferedImage image , double teta) {
        super(x , y , width , height );
        this.image = image;
        collided = false;
        shooting = false;
        dx = 0;
        dy = 0;
        angle = teta;
    }

    public void shoot(){
        shooting = true;
        state = 0;
     //   angle = Math.atan2(GameState.getTargetPoint().y + GameState.sY - y,  GameState.getTargetPoint().x + GameState.sX - x);
        dx = (bulletSpeed) * Math.cos(angle);
        dy = (bulletSpeed) * Math.sin(angle);
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

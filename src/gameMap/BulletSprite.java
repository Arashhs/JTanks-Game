package gameMap;

import bufferstrategy.GameState;
import bufferstrategy.Main;
import multiplayer.GameClient;
import multiplayer.GameServer;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class BulletSprite extends Sprite {
    protected int bulletSpeed;
    protected int damage;

    private boolean collided, shooting;
    private int source; //-1: player | 0: enemy

    public BulletSprite( int x , int y , int width , int height , BufferedImage image , double teta) {
        super(x , y , width , height );
        this.image = image;
        collided = false;
        shooting = false;
        dx = 0;
        dy = 0;
        angle = teta;
        source = 0;
    }

    public void shoot(){
        shooting = true;
        state = 0;
     //   angle = Math.atan2(GameState.getTargetPoint().y + GameState.sY - y,  GameState.getTargetPoint().x + GameState.sX - x);
        dx = (bulletSpeed) * Math.cos(angle);
        dy = (bulletSpeed) * Math.sin(angle);
    }

    public void move(Graphics2D g){
        Rectangle rec = new Rectangle(x,y,width,height);
        if( isCollidingWithSprite(rec) || isBulletColliding(rec)){
            collided = true;
            return;
        }
        x += dx;
        y += dy;
        drawRotated(angle , x - GameState.sX , y - GameState.sY , g);
    }

    public boolean isCollidingWithSprite(Rectangle r){
        for(MovingSprite ms : GameState.enemies.getMovingSprites()){
            if(!collided && source == -1 && r.intersects(ms)){
                ms.bulletHit(this);
                collided = true;
                return true;
            }
            else if(!collided && source == 0 && r.intersects(new Rectangle(GameState.tank.locX , GameState.tank.locY , GameState.tank.width , GameState.tank.height))){
                GameState.tank.bulletHit(this);
                collided = true;
                return true;
            }
            Rectangle otherTank;
            if(Main.connectionType == 0){
                otherTank = new Rectangle(GameServer.otherTank.locX , GameServer.otherTank.locY , GameState.tank.width , GameState.tank.height);
                if(otherTank.intersects(r)){
                    collided = true;
                    return true;
                }
            }
            else if(Main.connectionType == 1){
                otherTank = new Rectangle(GameClient.otherTank.locX , GameClient.otherTank.locY , GameState.tank.width , GameState.tank.height);
                if(otherTank.intersects(r)){
                    collided = true;
                    return true;
                }
            }
        }
        return false;
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

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}

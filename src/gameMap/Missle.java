package gameMap;

import bufferstrategy.GameState;

public class Missle extends Sprite {

    public static final int missleSpeed = 7;

    private int missleSpeedX , missleSpeedY;
    private boolean collided;

    public Missle() {
        super((int) (GameState.tank.getX()+33) , (int)(GameState.tank.getY() + 33) , 23 , 9 );
        image = Tile.missle;
        collided = false;
    }

    public void shoot(){
        //mouseX/Y = current x/y location of the mouse
        //originX/Y = x/y location of where the bullet is being shot from
        angle = Math.Atan2(mouseX - originX, mouseY - originY);
        double xVelocity = (bulletVelocity) * Math.Cos(angle);
        double yVelocity = (bulletVelocity) * Math.Sin(angle);
    }


}

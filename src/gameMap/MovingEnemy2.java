package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class MovingEnemy2 extends MovingSprite {
    private double speed;
    private int playerLocX, playerLocY;

    private static final int MOVING_ENEMY_DAMAGE = 100;

    public MovingEnemy2(int x , int y , int dx , int dy , BufferedImage image){
        super(x, y , 60 , 55 , image , null , GameState.enemies.getMovingSprites().size());
        state = STATE_Alive;
        this.dx = dx;
        this.dy = dy;
        hp = 120;
        baseImage = image;
        turretImage = null;
        turretAngle = 0;
        diam = 14;
        dx = 0;
        dy = 0;
        speed = 7;
    }

    public void tick(){
        if(collideWithTank){
            GameState.enemies.getMovingSprites().remove(this);
            GameState.tank.setHp(GameState.tank.getHp() - MOVING_ENEMY_DAMAGE);
            System.out.println(GameState.tank.getHp());
        }
        playerLocX = GameState.tank.locX;
        playerLocY = GameState.tank.locY;
        if(playerLocX - x >= 0) {
            dx = speed;
            image = Tile.movingEnemy2_4;
        }
        else{
            dx = -speed;
            image = Tile.movingEnemy2_2;
        }
        if(playerLocY - y >=0 ){
            dy = speed;
            image = Tile.movingEnemy2_1;
        }
        else {
            dy = -speed;
            image = Tile.movingEnemy2_3;
        }
        super.tick();
    }

    public void render(Graphics2D g2d , GameState state){
        g2d.drawImage(image, x - state.sX, y - state.sY, null);
    }
}
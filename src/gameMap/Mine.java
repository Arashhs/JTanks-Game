package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Mine extends MovingSprite {

    private int MINE_DAMAGE = 100;

    public Mine(int x , int y){
        super(x, y , 34 , 35 , Tile.mine , null , GameState.enemies.getMovingSprites().size());
        image = Tile.mine;
        hp = 10;
    }

    public void tick(){
        if(collideWithTank){
            GameState.enemies.getMovingSprites().remove(this);
            GameState.tank.setHp(GameState.tank.getHp() - MINE_DAMAGE);
        }
        super.tick();
    }

    public void render(Graphics2D g2d , GameState state){
        g2d.drawImage(image, x - state.sX, y - state.sY, null);
    }
}

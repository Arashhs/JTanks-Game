package gameMap;

import bufferstrategy.GameState;
import bufferstrategy.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class BigRedGun extends MovingSprite {
    private int time;
    private ArrayList<BulletSprite> bullets;

    public BigRedGun(int x , int y , BufferedImage im){
        super(x, y , 100 , 100 , Tile.bigRedGun , null , GameState.enemies.getMovingSprites().size());
        state = STATE_Alive;
        dx = dy = 0;
        hp = 600 * Main.gameDifficulty;
        baseImage = image = im;
        turretAngle = 0;
        diam = 14;
        time = 0;
        bullets = new ArrayList<>();
    }

    public void tick(){
        super.tick();
        time++;
        time %= 150;
        if(time == 0 && Math.abs(x - GameState.tank.locX) < 800 && Math.abs(y - GameState.tank.locY) < 800){
            BigMissile missile = new BigMissile(this , turretAngle);
            missile.shoot();
            bullets.add(missile);
        }

        for (int i = 0; i < bullets.size(); i++) {
            if (bullets.get(i).isCollided()) {
                bullets.remove(i);
                continue;
            }
        }
    }

    public void render(Graphics2D g , GameState state){
        turretAngle = Math.atan2(state.tank.locY - y - 10  ,  state.tank.locX - x - 10 ) ;
        drawRotated(turretAngle , x + 30 - GameState.sX , y + 30 - GameState.sY , g);
        for(BulletSprite b: bullets)
            b.move(g);
    }


}

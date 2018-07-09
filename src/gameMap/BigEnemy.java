package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Set;

public class BigEnemy extends MovingSprite {

    private int time;
    private ArrayList<BulletSprite> bullets;

    public BigEnemy(int x , int y , int dx , int dy , BufferedImage image){
        super(x, y , 100 , 100 , image , Tile.bigEnemyTurret , GameState.enemies.getMovingSprites().size());
        state = STATE_Alive;
        this.dx = dx;
        this.dy = dy;
        hp = 300;
        baseImage = image;
        turretImage = Tile.bigEnemyTurret;
        turretAngle = 0;
        diam = 14;
        time = 0;
        bullets = new ArrayList<>();
    }

    public void tick(){
        super.tick();
        time++;
        time %= 100;
        if(time == 0){
            Missile missile = new Missile(this , turretAngle);
            missile.shoot();
            bullets.add(missile);
        }
    }

    public void render(Graphics2D g , GameState state){
        super.render(g , state);
        for(BulletSprite b: bullets)
            b.move(g);
    }



}

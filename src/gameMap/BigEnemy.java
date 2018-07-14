package gameMap;

import bufferstrategy.GameState;
import bufferstrategy.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A moving type for enemies
 * @author Arash Hajisafi
 */
public class BigEnemy extends MovingSprite {

    private int time;
    private CopyOnWriteArrayList<BulletSprite> bullets;
    private boolean isVertical;

    public BigEnemy(int x , int y , int dx , int dy , BufferedImage image , boolean vert){
        super(x, y , 100 , 100 , image , Tile.bigEnemyTurret , GameState.enemies.getMovingSprites().size());
        state = STATE_Alive;
        this.dx = dx;
        this.dy = dy;
        hp = 300 * Main.gameDifficulty;
        baseImage = image;
        turretImage = Tile.bigEnemyTurret;
        turretAngle = 0;
        diam = 14;
        time = 0;
        bullets = new CopyOnWriteArrayList<>();
        isVertical = vert;
    }

    /**
     * Update all the actions for this enemy. Shoot at nearest player (MP) or player (SP)
     */
    public void tick(){
        if(Main.gameMode == 0) {
            super.tick();
            time++;
            time %= 100;
            if (time == 0 && Math.abs(x - GameState.tank.locX) < 800 && Math.abs(y - GameState.tank.locY) < 500) {
                Missile missile = new Missile(this, turretAngle);
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
        else if(Main.gameMode == 1){
            super.tick();
            time++;
            time %= 100;
            if (time == 0 && (distanceInteger(GameState.tank.locX , GameState.tank.locY , x , y) < 500 || distanceInteger(Main.otherTank.locX , Main.otherTank.locY , x , y) < 500 )) {
                Missile missile = new Missile(this, turretAngle);

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
    }

    /**
     * Renders this enemy
     * @param g Graphics object for the main frame
     * @param state current GameState
     */
    public void render(Graphics2D g , GameState state){
        super.render(g , state);
        for(BulletSprite b: bullets)
            b.move(g);
    }

    /**
     * Customized serialization
     * @param out outputStream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    /**
     * Customized serialization
     * @param in inputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if(isVertical)
            baseImage = Tile.bigEnemyBase2;
        else
            baseImage = Tile.bigEnemyBase1;
        turretImage = image = Tile.bigEnemyTurret;
    }

}

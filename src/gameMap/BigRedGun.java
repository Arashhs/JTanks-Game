package gameMap;

import bufferstrategy.GameState;
import bufferstrategy.Main;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class BigRedGun extends MovingSprite {
    private int time;
    private CopyOnWriteArrayList<BulletSprite> bullets;

    private boolean isRed;

    public BigRedGun(int x , int y , BufferedImage im , boolean red){
        super(x, y , 100 , 100 , Tile.bigRedGun , null , GameState.enemies.getMovingSprites().size());
        state = STATE_Alive;
        dx = dy = 0;
        hp = 600 * Main.gameDifficulty;
        baseImage = image = im;
        turretAngle = 0;
        diam = 14;
        time = 0;
        bullets = new CopyOnWriteArrayList<>();
        isRed = red;
    }

    public void tick(){
        if(Main.gameMode == 0) {
            super.tick();
            time++;
            time %= 150;
            if (time == 0 && Math.abs(x - GameState.tank.locX) < 800 && Math.abs(y - GameState.tank.locY) < 800) {
                BigMissile missile = new BigMissile(this, turretAngle);
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
            time %= 150;
            if (time == 0 && (distanceInteger(GameState.tank.locX , GameState.tank.locY , x , y) < 600 || distanceInteger(Main.otherTank.locX , Main.otherTank.locY , x , y) < 600 )) {
                BigMissile missile = new BigMissile(this, turretAngle);
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

    public void render(Graphics2D g , GameState state){
        if(Main.gameMode == 0) {
            turretAngle = Math.atan2(state.tank.locY - y - 10, state.tank.locX - x - 10);
            drawRotated(turretAngle, x + 30 - GameState.sX, y + 30 - GameState.sY, g);
            for (BulletSprite b : bullets)
                b.move(g);
        }
        else if(Main.gameMode == 1){
            if(distanceInteger(GameState.tank.locX , GameState.tank.locY , x , y) <= distanceInteger(Main.otherTank.locX , Main.otherTank.locY , x , y)) {
                turretAngle = Math.atan2(state.tank.locY - y - 10, state.tank.locX - x - 10);
            }
            else {
                turretAngle = Math.atan2(Main.otherTank.locY - y - 10, Main.otherTank.locX - x - 10);
            }
            drawRotated(turretAngle, x + 30 - GameState.sX, y + 30 - GameState.sY, g);
            for (BulletSprite b : bullets)
                b.move(g);
        }
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        if(isRed)
            image = baseImage = Tile.bigRedGun;
        else
            image = baseImage = Tile.blueGun;
    }


}

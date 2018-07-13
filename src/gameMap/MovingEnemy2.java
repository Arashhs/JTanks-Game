package gameMap;

import bufferstrategy.GameState;
import bufferstrategy.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MovingEnemy2 extends MovingSprite {
    private static final int MOVING_ENEMY_DAMAGE = 100;
    private double speed;
    private int playerLocX, playerLocY;

    public MovingEnemy2(int x, int y, int dx, int dy, BufferedImage image) {
        super(x, y, 60, 55, image, null, GameState.enemies.getMovingSprites().size());
        state = STATE_Alive;
        this.dx = dx;
        this.dy = dy;
        hp = 120 * Main.gameDifficulty;
        baseImage = image;
        turretImage = null;
        turretAngle = 0;
        diam = 14;
        dx = 0;
        dy = 0;
        speed = 7;
    }

    public void tick() {
        if (Main.gameMode == 0) {
            if (collideWithTank) {
                GameState.enemies.getMovingSprites().remove(this);
                GameState.tank.setHp(GameState.tank.getHp() - MOVING_ENEMY_DAMAGE);
                System.out.println(GameState.tank.getHp());
            }
            playerLocX = GameState.tank.locX;
            playerLocY = GameState.tank.locY;
            if (playerLocX - x >= 0) {
                dx = speed;
                image = Tile.movingEnemy2_4;
            } else {
                dx = -speed;
                image = Tile.movingEnemy2_2;
            }
            if (playerLocY - y >= 0) {
                dy = speed;
                image = Tile.movingEnemy2_1;
            } else {
                dy = -speed;
                image = Tile.movingEnemy2_3;
            }
            super.tick();
        } else if (Main.gameMode == 1) {
            if (collideWithOther) {
                GameState.enemies.getMovingSprites().remove(this);
                System.out.println(GameState.tank.getHp());
            } else if (collideWithTank) {
                GameState.enemies.getMovingSprites().remove(this);
                GameState.tank.setHp(GameState.tank.getHp() - MOVING_ENEMY_DAMAGE);
                System.out.println(GameState.tank.getHp());
            }
            if(distanceInteger(GameState.tank.locX , GameState.tank.locY , x , y) <= distanceInteger(Main.otherTank.locX , Main.otherTank.locY , x , y)) {
                playerLocX = GameState.tank.locX;
                playerLocY = GameState.tank.locY;
            }
            else {
                playerLocX = Main.otherTank.locX;
                playerLocY = Main.otherTank.locY;
            }
            if (playerLocX - x >= 0) {
                dx = speed;
                image = Tile.movingEnemy2_4;
            } else {
                dx = -speed;
                image = Tile.movingEnemy2_2;
            }
            if (playerLocY - y >= 0) {
                dy = speed;
                image = Tile.movingEnemy2_1;
            } else {
                dy = -speed;
                image = Tile.movingEnemy2_3;
            }
            super.tick();
        }
    }

    public void render(Graphics2D g2d, GameState state) {
        g2d.drawImage(image, x - state.sX, y - state.sY, null);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        baseImage = Tile.movingEnemy2_1;
        turretImage = null;
    }

}

package gameMap;

import bufferstrategy.GameState;
import bufferstrategy.Main;
import multiplayer.GameClient;
import multiplayer.GameServer;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * MovingSprite that is the father of all moving sprites (Player - enemies) + Mine (Exception!)
 * @author Arash
 */
public abstract class MovingSprite extends Sprite {
    protected int hp, diam;
    protected double turretAngle;
    protected int id;

    protected boolean collideWithTank;
    protected boolean collideWithOther;

    private PickUp deathLoot;

    protected transient BufferedImage baseImage;
    protected transient BufferedImage turretImage;

    public MovingSprite(int x, int y, int width, int height, BufferedImage baseImage, BufferedImage turretImage , int id) {
        super(x, y, width, height);
        this.baseImage = baseImage;
        this.turretImage = turretImage;
        turretAngle = 0;
        image = turretImage;
        this.id = id;
        collideWithTank = false;
        collideWithOther = false;
        deathLoot = null;
    }

    /**
     * Controls whether or not a moving sprite collides with another movingSprite
     * @param rectangle movingSprite we want to check whether or not it collides with another one
     * @return
     */
    public boolean isColliding(Rectangle rectangle) {
        Rectangle tank = new Rectangle(GameState.tank.locX , GameState.tank.locY , GameState.tank.width , GameState.tank.height);
        Rectangle otherTank;
        if(Main.gameMode == 1){
            otherTank = new Rectangle(Main.otherTank.locX , Main.otherTank.locY , GameState.tank.width , GameState.tank.height);
            if(otherTank.intersects(rectangle)){
                collideWithTank = true;
                collideWithOther = true;
                return true;
            }
        }
        if(tank.intersects(rectangle)) {
            collideWithTank = true;
            return true;
        }
        for(MovingSprite ms : GameState.enemies.getMovingSprites()){
            if(ms.getBounds2D().intersects(rectangle.getBounds2D()) && ms.id != this.id)
                return true;
        }
        for (int i = Math.max((int) ((x) / Tile.tileSize) - 1, 0); i < Math.min((int) ((x + width) / Tile.tileSize) + 2, GameState.level.blocks.length); i++) {
            for (int j = Math.max((int) ((y) / Tile.tileSize) - 1, 0); j < Math.min((int) ((y + height) / Tile.tileSize) + 2, GameState.level.blocks[i].length); j++) {
                if (GameState.level.blocks[i][j].isCollidable() && rectangle.intersects(GameState.level.blocks[i][j])) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Updates the location of movingSprite
     */
    public void tick() {
        if(Main.gameMode == 0) {
            if (Math.abs(x - GameState.tank.locX) < 800 && Math.abs(y - GameState.tank.locY) < 800) {
                update();
                if (!isColliding(new Rectangle((int) (x + dx), (int) (y + dy), width, height))) {
                    x += dx;
                    y += dy;
                } else {
                    dx = -dx;
                    dy = -dy;
                }
            }
        }
        else if(Main.gameMode == 1){
            if (distanceInteger(GameState.tank.locX , GameState.tank.locY , x , y) < 1100 || distanceInteger(Main.otherTank.locX , Main.otherTank.locY , x , y) < 1100  ) {
                update();
                if (!isColliding(new Rectangle((int) (x + dx), (int) (y + dy), width, height))) {
                    x += dx;
                    y += dy;
                } else {
                    dx = -dx;
                    dy = -dy;
                }
            }
        }
    }

    /**
     * Renders and shows the movingSprite
     * @param g2d Graphic's object
     * @param state Current game state
     */
    public void render(Graphics2D g2d, GameState state) {

        if (Main.gameMode == 0) {
            turretAngle = Math.atan2(state.tank.locY - y - 36, state.tank.locX - x - 30);
            g2d.drawImage(baseImage, x - state.sX, y - state.sY, null);
            drawRotated(turretAngle, x + 30 - GameState.sX, y + 33 - GameState.sY, g2d);
            //  g2d.drawImage(image, x + 30 - state.sX, y + 33 - state.sY, null);
        }
        else if(Main.gameMode == 1){
            if(distanceInteger(GameState.tank.locX , GameState.tank.locY , x , y) <= distanceInteger(Main.otherTank.locX , Main.otherTank.locY , x , y)) {
               turretAngle = Math.atan2(state.tank.locY - y - 36, state.tank.locX - x - 30);
            }
            else {
                turretAngle = Math.atan2(Main.otherTank.locY - y - 36, Main.otherTank.locX - x - 30);
            }
            g2d.drawImage(baseImage, x - state.sX, y - state.sY, null);
            drawRotated(turretAngle, x + 30 - GameState.sX, y + 33 - GameState.sY, g2d);
            //  g2d.drawImage(image, x + 30 - state.sX, y + 33 - state.sY, null);
        }
    }

    /**
     * Draws the rotated turret for moving sprite (Used only for enemies)
     * @param angle angle of the turret
     * @param x x Coordinate of the place we want to draw
     * @param y y Coordinate of the place we want to draw
     * @param g Graphic's object
     */
    public void drawRotated(double angle , int x , int y , Graphics2D g){
        backupAt = g.getTransform();
        at = new AffineTransform();
        at.rotate(angle, x+ 20 ,y+20 );
        g.setTransform(at);
        g.drawImage(image , x , y ,null);
        g.setTransform(backupAt);
    }

    /**
     * Simulates the bullet hitting player
     * @param bullet bullet that hit player
     */
    public void bulletHit(BulletSprite bullet){
        hp -= bullet.damage;
        if(hp <= 0)
            state = STATE_Dead;

    }

    /**
     * Updates the sprite based on its state (Dead or alive)
     * removes the dead enemies from screen
     */
    public void update(){
        if (state == STATE_Dead) {
            GameState.lastEvent = "Enemy destroyed!";
            GameState.loots.deathLoot(x , y);
            GameState.enemies.getMovingSprites().remove(this);
        }
    }

    /* *******Getter and Setters******** */
    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public double getTurretAngle() {
        return turretAngle;
    }
    /* *******Getter and Setters******** */


    /**
     * Calculates the distance between two coordinates
     * @param x1 x of first point
     * @param y1 y of first point
     * @param x2 x of the second point
     * @param y2 y of the second point
     * @return dinstance in integer
     */
    public int distanceInteger(int x1 , int y1 , int x2 , int y2){
        return  (int) Math.hypot(x1-x2, y1-y2);
    }
}

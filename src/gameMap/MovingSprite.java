package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class MovingSprite extends Sprite {
    protected int hp, diam;
    protected BufferedImage baseImage;
    protected BufferedImage turretImage;
    protected double turretAngle;
    protected int id;

    protected boolean collideWithTank;

    public MovingSprite(int x, int y, int width, int height, BufferedImage baseImage, BufferedImage turretImage , int id) {
        super(x, y, width, height);
        this.baseImage = baseImage;
        this.turretImage = turretImage;
        turretAngle = 0;
        image = turretImage;
        this.id = id;
        collideWithTank = false;
    }

    public boolean isColliding(Rectangle rectangle) {
        Rectangle tank = new Rectangle(GameState.tank.locX , GameState.tank.locY , GameState.tank.width , GameState.tank.height);
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

    public void tick() {
        update();
        if (!isColliding(new Rectangle((int)(x + dx ) ,(int) (y + dy ), width, height))) {
            x += dx;
            y += dy;
        }
        else {
            dx = -dx;
            dy = -dy;
        }
    }

    public void render(Graphics2D g2d, GameState state) {
         turretAngle = Math.atan2(state.tank.locY - y - 36  ,  state.tank.locX - x - 30 ) ;
         g2d.drawImage(baseImage, x - state.sX, y - state.sY, null);
         drawRotated(turretAngle , x + 30 - GameState.sX , y + 33 - GameState.sY , g2d);
      //  g2d.drawImage(image, x + 30 - state.sX, y + 33 - state.sY, null);
    }

    public void drawRotated(double angle , int x , int y , Graphics2D g){
        backupAt = g.getTransform();
        at = new AffineTransform();
        at.rotate(angle, x+ 20 ,y+20 );
        g.setTransform(at);
        g.drawImage(image , x , y ,null);
        g.setTransform(backupAt);
    }

    public void bulletHit(BulletSprite bullet){
        hp -= bullet.damage;
        if(hp <= 0)
            state = STATE_Dead;

    }

    public void update(){
        if (state == STATE_Dead)
            GameState.enemies.getMovingSprites().remove(this);
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}

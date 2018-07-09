package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class Sprite extends Rectangle {
    protected int x, y, width, height, diam;
    protected double angle;
    protected BufferedImage image;

    // velocity (pixels per millisecond)
    protected double dx;
    protected double dy;

    protected int state; //0: Alive | 1: Dead

    public static final int STATE_Dead = 1;
    public static final int STATE_Alive = 0;

    private AffineTransform backupAt;
    private AffineTransform at;

    public Sprite(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setBounds(x, y, width, height);
        dx = 0;
        dy = 0;
    }

    public Sprite(Rectangle r) {
        super(r);
        dx = 0;
        dy = 0;
    }

    public void drawRotated(double angle , int x , int y , Graphics2D g){
        backupAt = g.getTransform();
        at = new AffineTransform();
        at.rotate(angle, x ,y );
        g.setTransform(at);
        g.drawImage(image , x , y ,null);
        g.setTransform(backupAt);
    }

    public boolean isBulletColliding(Rectangle rectangle) {
        for (int i = Math.max((int) ((x) / Tile.tileSize) - 2 , 0); i < Math.min((int) ((x + width) / Tile.tileSize) + 2, GameState.level.blocks.length); i++) {
            for (int j = Math.max((int) ((y) / Tile.tileSize) - 2, 0); j < Math.min((int) ((y+ height) / Tile.tileSize) + 2, GameState.level.blocks[i].length); j++) {
                    if ((GameState.level.blocks[i][j].isBulletCollidable()) && (rectangle.intersects(GameState.level.blocks[i][j]))) {
                        if(GameState.level.blocks[i][j].isDestructable()){
                            GameState.level.blocks[i][j].nextId();
                        }
                        return true;
                    }
            }
        }

        return false;
    }


    public int getDiam() {
        return diam;
    }

    public void setDiam(int diam) {
        this.diam = diam;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
}

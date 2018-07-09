package gameMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Sprite extends Rectangle {
    protected int x, y, width, height, diam;
    protected double angle;
    protected BufferedImage image;

    // velocity (pixels per millisecond)
    protected double dx;
    protected double dy;

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

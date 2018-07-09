package gameMap;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MovingSprite extends Sprite {
    protected int hp;

    public MovingSprite(int x , int y , int width , int height , BufferedImage baseImage , BufferedImage turretImage){
        super(x , y , width , height);
    }

    public void tick(){

    }

    public void render(Graphics2D g2d){
        g2d.drawImage(image, x , y, null);
    }
}

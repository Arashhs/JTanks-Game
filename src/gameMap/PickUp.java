package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public abstract class PickUp extends Sprite {
    protected boolean pickedUp;

    public static final int AMMO_RARITY = 20;
    public static final int REPAIR_KIT_RARITY = 10;
    public static final int UPGRADER_RARITY = 5;


    public PickUp(int x , int y , int width , int height , BufferedImage image){
        super(x , y , width , height);
        this.image = image;
        pickedUp = false;
    }

    public boolean isColliding(Rectangle rect) {
        Rectangle tank = new Rectangle(GameState.tank.locX , GameState.tank.locY , GameState.tank.width , GameState.tank.height);
        if(tank.intersects(rect)) {
            pickedUp = true;
            return true;
        }
        return false;
    }

    public void update(GameState gState , Graphics2D g2d){
        if (isColliding(this)) {
            tick(gState , g2d);
            GameState.loots.getLoots().remove(this);
        }
    }

    public void tick(GameState gState , Graphics2D g2d){
    }

    public void render(Graphics2D g2d, GameState state) {
        update(state , g2d);
        g2d.drawImage(image, x - state.sX, y - state.sY, null);
        //  g2d.drawImage(image, x + 30 - state.sX, y + 33 - state.sY, null);
    }

}

package gameMap;

import bufferstrategy.GameState;
import bufferstrategy.Main;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Simulates a moving sprite (Enemy - player)
 * @author Arash
 */
public class Mine extends MovingSprite {

    private int MINE_DAMAGE = 100;
    private boolean blast;

    public Mine(int x , int y){
        super(x, y , 34 , 35 , Tile.mine , null , GameState.enemies.getMovingSprites().size());
        image = Tile.mine;
        hp = 10;
        blast =false;
    }

    /**
     * Updates the mine state
     */
    public void tick(){
        if(Main.connectionType == 0){
            if(this.intersects(new Rectangle(Main.otherTank.locX   , Main.otherTank.locY  , Main.otherTank.width + 10 , Main.otherTank.height + 10))){
                blast = !blast;
                if(!blast){
                    GameState.enemies.getMovingSprites().remove(this);
                }
            }


        }
        if(collideWithTank){
            GameState.enemies.getMovingSprites().remove(this);
            GameState.tank.setHp(GameState.tank.getHp() - MINE_DAMAGE);
            state = STATE_Dead;
        }
        super.tick();
    }

    /**
     * Renders and draws the mine
     * @param g2d graphic's object
     * @param state current game state
     */
    public void render(Graphics2D g2d , GameState state){
        g2d.drawImage(image, x - state.sX, y - state.sY, null);
    }

    /**
     * Customized serialization
     * @param out outPutStream
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
        baseImage = image = Tile.mine;
    }
}

package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Block class for each tile
 * @author Arash
 */
public class Block extends Rectangle {
    private int id; //Specify block's type (Dirt , grass , etc)
    private boolean collidable;
    private boolean destructable;
    private boolean bulletCollidable;

    private transient BufferedImage image;


    public Block(Rectangle r, int id , BufferedImage image) {
        setBounds(r);
        this.id = id;
        collidable = false;
        destructable = false;
        bulletCollidable = false;
        this.image = image;
        init();
    }

    /**
     * Display tile on screen
     * @param g
     */
    public void render(Graphics2D g) {
      /*  g.drawImage(Tile.tileSet_soil, x - GameState.sX, y - GameState.sY, x + width - GameState.sX, y + height - GameState.sY, x, y, x + Tile.tileSize, y + Tile.tileSize, null);
        if (id == 0)
        g.drawImage(Tile.tileSet_soil , x - GameState.sX , y - GameState.sY , Tile.tileSize , Tile.tileSize , null);
        else
            g.drawImage(Tile.base , x - GameState.sX , y - GameState.sY , Tile.tileSize , Tile.tileSize , null); */
      g.drawImage(image , x - GameState.sX , y - GameState.sY , Tile.tileSize , Tile.tileSize , null);

    }

    /**
     * Initilizes tile's specifications based on its ID
     */
    public void init(){
        char c = (char) ('A' + id);
        switch (c){
            case 'A':
            case 'H':
                bulletCollidable = false;
                collidable = false;
                destructable = false;
                break;
            case 'B':
                bulletCollidable = true;
                collidable = true;
                destructable = false;
                break;
            case 'I':
            case 'C':
            case 'J':
                collidable = true;
                bulletCollidable = false;
                destructable = false;
                break;
            case 'D':
            case 'E':
            case 'F':
            case 'G':
                collidable = true;
                destructable = true;
                bulletCollidable = true;
                break;
        }
    }

    /* ********Getter and Setters******** */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void nextId(){
        id++;
        image = Tile.tileImages.get(id);
        init();
    }

    public boolean isCollidable() {
        return collidable;
    }

    public void setCollidable(boolean collidable) {
        this.collidable = collidable;
    }

    public boolean isDestructable() {
        return destructable;
    }

    public void setDestructable(boolean destructable) {
        this.destructable = destructable;
    }

    public boolean isBulletCollidable() {
        return bulletCollidable;
    }

    public void setBulletCollidable(boolean bulletCollidable) {
        this.bulletCollidable = bulletCollidable;
    }
    /* ********Getter and Setters******** */

    /**
     * Customized serialization
     * @param out outputstream
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
        image = Tile.tileImages.get(id);
    }

}

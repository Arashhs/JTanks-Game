package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Block extends Rectangle {
    private BufferedImage image;
    private int id; //Specify block's type (Dirt , grass , etc)
    private boolean collidable;
    private boolean destructable;

    public Block(Rectangle r, int id , BufferedImage image) {
        setBounds(r);
        this.id = id;
        collidable = false;
        destructable = false;
        this.image = image;
    }

    public void render(Graphics2D g) {
      /*  g.drawImage(Tile.tileSet_soil, x - GameState.sX, y - GameState.sY, x + width - GameState.sX, y + height - GameState.sY, x, y, x + Tile.tileSize, y + Tile.tileSize, null);
        if (id == 0)
        g.drawImage(Tile.tileSet_soil , x - GameState.sX , y - GameState.sY , Tile.tileSize , Tile.tileSize , null);
        else
            g.drawImage(Tile.base , x - GameState.sX , y - GameState.sY , Tile.tileSize , Tile.tileSize , null); */
      g.drawImage(image , x - GameState.sX , y - GameState.sY , Tile.tileSize , Tile.tileSize , null);

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}

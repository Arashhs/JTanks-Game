package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

public class Block extends Rectangle {
    public int id; //Specify block's type (Dirt , grass , etc)
    private boolean collidable;
    private boolean destructable;

    public Block(Rectangle r, int id) {
        setBounds(r);
        this.id = id;
    }

    public void render(Graphics2D g) {
        g.drawImage(Tile.tileSet_soil, x - GameState.sX, y - GameState.sY, x + width - GameState.sX, y + height - GameState.sY, x, y, x + Tile.tileSize, y + Tile.tileSize, null);
        System.out.println(id);
    }
    }

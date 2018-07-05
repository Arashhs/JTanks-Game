package gameMap;

import java.awt.*;

public class Block extends Rectangle {
    public int id; //Specify block's type (Dirt , grass , etc)

    public Block(Rectangle r, int id) {
        setBounds(r);
        this.id = id;
    }

    public void render(Graphics2D g) {
        g.drawImage(Tile.tileSet_soil, x, y, x + width , y + height , x , y , x + Tile.tileSize , y + Tile.tileSize , null);
    }
}

package gameMap;

import java.awt.*;

public class Block extends Rectangle {
    public int id; //Specify block's type (Dirt , grass , etc)

    public Block(Rectangle r , int id) {
        setBounds(r);
        this.id = id;
    }

    public void render(Graphics g){
        g.fillRect(x, y, width, height);
    }
}

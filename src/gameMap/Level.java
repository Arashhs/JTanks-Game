package gameMap;

import java.awt.*;
import java.util.ArrayList;

public class Level {
   // public ArrayList<ArrayList<Block>> blocks;
    public Block[][] blocks = new Block[50][50];

    public Level() {
    /*    blocks = new ArrayList<>(50);
        for(ArrayList<Block> b : blocks){
            b = new ArrayList<>(50);
        } */
    for(int x = 0 ; x < blocks.length ; x++){
        for(int y = 0 ; y < blocks[0].length ; y++){
            blocks[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , Tile.soil);
        }
    }
    }

    public void tick(){

    }
    public void render(Graphics g){
        for(int x = 0 ; x < blocks.length ; x++){
            for(int y = 0 ; y < blocks[0].length ; y++){
                blocks[x][y].render(g);
            }
        }
    }
}

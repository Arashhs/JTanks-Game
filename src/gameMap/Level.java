package gameMap;

import bufferstrategy.GameFrame;
import bufferstrategy.GameState;

import java.awt.*;
import java.util.ArrayList;

public class Level {
   // public ArrayList<ArrayList<Block>> blocks;
    public Block[][] blocks = new Block[500][500];

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
    blocks[0][0].setId(1);
    blocks[5][5].setId(1);
    blocks[0][0].setCollidable(true);
    blocks[5][5].setCollidable(true);
    }

    public void tick(){

    }



    public void render(Graphics2D g){

        for (int x = Math.max ((GameState.tank.locX / Tile.tileSize - 9) , 0 )  ; x < Math.min( (GameState.tank.locX / Tile.tileSize) + 13 , blocks[0].length)  ; x++) {
           for (int y = Math.max ((GameState.tank.locY / Tile.tileSize - 6) , 0 )  ; y < Math.min( (GameState.tank.locY / Tile.tileSize) + 13 , blocks.length)  ; y++){
                blocks[x][y].render(g);
            }
        }
    }
}

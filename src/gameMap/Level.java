package gameMap;

import bufferstrategy.GameFrame;
import bufferstrategy.GameState;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Level {
   // public ArrayList<ArrayList<Block>> blocks;
    public Block[][] blocks = new Block[500][500];

    public Level() {

  /*  for(int x = 0 ; x < blocks.length ; x++){
        for(int y = 0 ; y < blocks[0].length ; y++){
            blocks[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , Tile.soil , Tile.tileSet_soil);
        }
    }
    blocks[0][0].setId(1);
    blocks[5][5].setId(1);
    blocks[0][0].setCollidable(true);
    blocks[5][5].setCollidable(true); */
        try {
            blocks = loadMap("res\\maps\\map.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tick(){

    }



    public void render(Graphics2D g){
        for (int x = Math.max ((GameState.tank.locX / Tile.tileSize - 9) , 0 )  ; x < Math.min( (GameState.tank.locX / Tile.tileSize) + 13 , blocks.length)  ; x++) {
           for (int y = Math.max ((GameState.tank.locY / Tile.tileSize - 6) , 0 )  ; y < Math.min( (GameState.tank.locY / Tile.tileSize) + 13 , blocks[x].length)  ; y++){
                  blocks[x][y].render(g);
            }
        }
    }

    public Block[][] loadMap(String filename) throws IOException {
        ArrayList<String> lines = new ArrayList();
        int width = 0;
        int height = 0;
        // read every line in the text file into the list
        BufferedReader reader = new BufferedReader(
                new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }
            // add every line except for comments
            if (!line.startsWith("#")) {
                lines.add(line);
                width = Math.max(width, line.length());
            }
        }
        // parse the lines to create a TileEngine
        height = lines.size();
        Block[][] newMap = new Block[height][width];
        for (int y=0; y<height; y++) {
            String line = lines.get(y);
            for (int x=0; x<width; x++) {
                blocks[y][x] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , Tile.soil , Tile.tileSet_soil);
                char ch = line.charAt(x);

                // check if the char represents tile A, B, C, etc.
                if(ch == ' ')
                    ch = 'A';
                int tile = ch - 'A';
                if (tile >= 0 && tile < Tile.tileImages.size()) {
                    newMap[y][x] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , tile , Tile.tileImages.get(tile));
                }
                // check if the char represents a sprite
          /*      else if (ch == 'o') {
                    addSprite(newMap, coinSprite, x, y);
                }
                else if (ch == '!') {
                    addSprite(newMap, musicSprite, x, y);
                }
                else if (ch == '*') {
                    addSprite(newMap, goalSprite, x, y);
                }
                else if (ch == '1') {
                    addSprite(newMap, grubSprite, x, y);
                }
                else if (ch == '2') {
                    addSprite(newMap, flySprite, x, y);
                }  */
            }
        }
        // add the player to the map
 /*      Sprite player = (Sprite)playerSprite.clone();
        player.setX(TileMapRenderer.tilesToPixels(3));
        player.setY(0);
        newMap.setPlayer(player); */
        return newMap;
    }
}

package gameMap;

import bufferstrategy.GameFrame;
import bufferstrategy.GameState;
import bufferstrategy.Main;
import java.lang.String;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Level class for game
 * This holds the information of all blocks (Tiles)
 * This also loads map
 * @author Arash
 */
public class Level implements Serializable {
   // public ArrayList<ArrayList<Block>> blocks;
    public Block[][] blocks = new Block[500][500];
    private int width, height;

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
            if(Main.levelSelection == 0){
                blocks = loadMap("res\\maps\\map_edited.txt");
            }
            else if(Main.levelSelection == 1) {
                if (Main.gameDifficulty == 1)
                    blocks = loadMap("res\\maps\\map1_easy.txt");
                else if (Main.gameDifficulty == 2)
                    blocks = loadMap("res\\maps\\map1_medium.txt");
                else if (Main.gameDifficulty == 3)
                    blocks = loadMap("res\\maps\\map1_hard.txt");
            }
            else if(Main.levelSelection == 2){
                if (Main.gameDifficulty == 1)
                    blocks = loadMap("res\\maps\\map2_easy.txt");
                else if (Main.gameDifficulty == 2)
                    blocks = loadMap("res\\maps\\map2_medium.txt");
                else if (Main.gameDifficulty == 3)
                    blocks = loadMap("res\\maps\\map2_hard.txt");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This updates the state for tank
     * @param state GameState for main game
     */
    public void tick(GameState state){
        if(GameState.tank.getHp() <= 0) {
            GameState.tank.setHp(0);
            state.setGameOver(1);
        }
    }


    /**
     * Render and show the tiles in the game
     * @param g Graphic's object
     * @param state Current gameState
     */
    public void render(Graphics2D g , GameState state){
        for (int i = Math.max ((state.tank.locX / Tile.tileSize - 9) , 0 )  ; i < Math.min( (state.tank.locX / Tile.tileSize) + 13 , blocks.length)  ; i++) {
           for (int j = Math.max ((state.tank.locY / Tile.tileSize - 6) , 0 )  ; j < Math.min( (state.tank.locY / Tile.tileSize) + 13 , blocks[i].length)  ; j++){
               blocks[i][j].render(g);
            }
        }
    }

    /**
     * Loads map from a text file
     * @param filename text file path
     * @return List of all tiles (Blocks)
     * @throws IOException
     */
    public Block[][] loadMap(String filename) throws IOException {
        ArrayList<String> lines = new ArrayList();
        width = 0;
        height = 0;
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
        Block[][] newMap = new Block[width][height];
        for (int y=0; y<height; y++) {
            String line = lines.get(y);
            for (int x=0; x<width; x++) {
                char ch = 'A';
               try {
                    ch = line.charAt(x);
               }
               catch (StringIndexOutOfBoundsException e){
                   ch = 'B';
               }

                // check if the char represents tile A, B, C, etc.
                if(ch == ' ' || ch == 0)
                    ch = 'A';
                int tile = ch - 'A';
                if (tile >= 0 && tile < Tile.tileImages.size()) {
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , tile , Tile.tileImages.get(tile));
                }
                // check if the char represents a sprite
                else if (ch == 'O') {
                    GameState.enemies.addEnemy('O' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '!') {
                    GameState.enemies.addEnemy('I' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '1') {
                    GameState.enemies.addEnemy('1' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '#') {
                    GameState.enemies.addEnemy('#' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '$') {
                    GameState.enemies.addEnemy('$' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) ,0 , Tile.tileImages.get(0));
                }
                else if (ch == '%') {
                    GameState.enemies.addEnemy('%' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '^') {
                    GameState.loots.addPickUp('^' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '&') {
                    GameState.loots.addPickUp('&' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '*') {
                    GameState.loots.addPickUp('*' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
                else if (ch == '(') {
                    GameState.loots.addPickUp('(' , x , y);
                    newMap[x][y] = new Block(new Rectangle(x * Tile.tileSize , y * Tile.tileSize , Tile.tileSize , Tile.tileSize) , 0 , Tile.tileImages.get(0));
                }
             /*   else if (ch == '!') {
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

    /**
     * Converts current map data (Blocks) to String to save and load easily
     * @return Map data
     */
    public java.lang.String mapToText(){
        ArrayList<String> lines = new ArrayList();
        StringBuilder line = new StringBuilder();
        for(int i = 0 ; i < height ; i++){
            for(int j = 0 ; j < width ; j++){
                line.append(blocks[j][i].getId());
            }
            line.append("\n");
        }
        System.out.println(line.toString());
        return line.toString();
    }

}

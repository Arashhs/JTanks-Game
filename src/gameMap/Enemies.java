package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class holds the state for all the enemies within the game
 * @author Arash
 */
public class Enemies implements Serializable{
    public static final int EASY_Difficulty = 0;
    public static final int MEDIUM_DIFFICULTY = 1;
    public static final int HARD_DIFFICULTY = 2;

    private static int difficulty;

    private CopyOnWriteArrayList<MovingSprite> movingSprites;

    public Enemies(int difficulty){
        movingSprites = new CopyOnWriteArrayList<>();
    }

    /**
     * Calculates a coordinate based on the tile number
     * @param tileWidth each tile Width
     * @param tileHeight each tile height
     * @return
     */
    public Point position(int tileWidth , int tileHeight){
        return new Point(tileWidth*Tile.tileSize , tileHeight*Tile.tileSize);
    }

    /**
     * Add enemy from text map based on its ID
     * @param type type or ID for each enemy
     * @param tileWidth each tile's width
     * @param tileHeight each tile's height
     */
    public void addEnemy(char type , int tileWidth , int tileHeight){
        Point p = position(tileWidth , tileHeight);
        if(type == 'O'){
            movingSprites.add(new BigEnemy(p.x , p.y , 5 , 0 , Tile.bigEnemyBase1 , false) );
        }
        else if(type == 'I'){
            movingSprites.add(new BigEnemy(p.x , p.y , 0 , 5 , Tile.bigEnemyBase2 , true));
        }
        else if(type == '1'){
            movingSprites.add(new MovingEnemy2(p.x , p.y , 0 , 5 , Tile.movingEnemy2_1));
        }
        else if(type == '#'){
            movingSprites.add(new Mine(p.x , p.y));
        }
        else if(type == '$'){
            movingSprites.add(new BigRedGun(p.x , p.y , Tile.bigRedGun , true));
        }
        else if(type == '%'){
            movingSprites.add(new BigRedGun(p.x , p.y , Tile.blueGun , false));
        }
    }

    /**
     * Update actions for all enemies
     * @param gState Gamestate for the player
     */
    public void tick(GameState gState){

        for(MovingSprite movingSprite : movingSprites){
            movingSprite.tick();
        }
        if(movingSprites.size() < 1)
            gState.setGameOver(2);

    }

    /**
     * Render all the enemies on screen
     * @param g2d graphics object for main frame
     * @param state Gamestate for player
     */
    public void render(Graphics2D g2d , GameState state){
        for(MovingSprite movingSprite : movingSprites){
            movingSprite.render(g2d , state);
        }

    }

    /**
     * getter
     * @return Enemies array
     */
    public CopyOnWriteArrayList<MovingSprite> getMovingSprites() {
        return movingSprites;
    }
}

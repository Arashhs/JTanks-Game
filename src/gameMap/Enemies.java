package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.util.ArrayList;

public class Enemies{
    public static final int EASY_Difficulty = 0;
    public static final int MEDIUM_DIFFICULTY = 1;
    public static final int HARD_DIFFICULTY = 2;

    private static int difficulty;

    private ArrayList<MovingSprite> movingSprites;

    public Enemies(int difficulty){
        movingSprites = new ArrayList<>();
    }

    public Point position(int tileWidth , int tileHeight){
        return new Point(tileWidth*Tile.tileSize , tileHeight*Tile.tileSize);
    }

    public void addEnemy(char type , int tileWidth , int tileHeight){
        Point p = position(tileWidth , tileHeight);
        if(type == 'O'){
            movingSprites.add(new BigEnemy(p.x , p.y , 5 , 0 , Tile.bigEnemyBase1) );
        }
        else if(type == 'I'){
            movingSprites.add(new BigEnemy(p.x , p.y , 0 , 5 , Tile.bigEnemyBase2));
        }
    }

    public void tick(){

        for(MovingSprite movingSprite : movingSprites){
            movingSprite.tick();
        }
    }

    public void render(Graphics2D g2d , GameState state){
        for(MovingSprite movingSprite : movingSprites){
            movingSprite.render(g2d , state);
        }

    }

    public ArrayList<MovingSprite> getMovingSprites() {
        return movingSprites;
    }
}

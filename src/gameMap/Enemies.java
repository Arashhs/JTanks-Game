package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Enemies implements Serializable{
    public static final int EASY_Difficulty = 0;
    public static final int MEDIUM_DIFFICULTY = 1;
    public static final int HARD_DIFFICULTY = 2;

    private static int difficulty;

    private CopyOnWriteArrayList<MovingSprite> movingSprites;

    public Enemies(int difficulty){
        movingSprites = new CopyOnWriteArrayList<>();
    }

    public Point position(int tileWidth , int tileHeight){
        return new Point(tileWidth*Tile.tileSize , tileHeight*Tile.tileSize);
    }

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

    public void tick(GameState gState){

        for(MovingSprite movingSprite : movingSprites){
            movingSprite.tick();
        }
        if(movingSprites.size() < 1)
            gState.setGameOver(2);

    }

    public void render(Graphics2D g2d , GameState state){
        for(MovingSprite movingSprite : movingSprites){
            movingSprite.render(g2d , state);
        }

    }

    public CopyOnWriteArrayList<MovingSprite> getMovingSprites() {
        return movingSprites;
    }
}

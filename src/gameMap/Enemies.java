package gameMap;

import java.util.ArrayList;

public class Enemies{
    public static final int EASY_Difficulty = 0;
    public static final int MEDIUM_DIFFICULTY = 1;
    public static final int HARD_DIFFICULTY = 2;

    private static int difficulty;

    private ArrayList<MovingSprite> movingSprites;

    public Enemies(int difficulty){
        this.difficulty = difficulty;
        movingSprites = new ArrayList<MovingSprite>();
    }
}

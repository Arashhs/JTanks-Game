package gameMap;

public class BigEnemy extends MovingSprite {

    public BigEnemy(int x , int y , int dx , int dy){
        super(x, y , 100 , 100 , Tile.bigEnemyBase , Tile.bigEnemyTurret);
        state = STATE_Alive;
        this.dx = dx;
        this.dy = dy;
    }

    public void tick(){

    }

}

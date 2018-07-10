package gameMap;

import java.awt.*;

public class BigMissile extends BulletSprite{
    public BigMissile(Rectangle source , double teta) {
        super((int) (source.x + 50) , (int)(source.y + 50) , 50 , 29 , Tile.bigMissile , teta);
        bulletSpeed = 5;
        damage = 300;
    }
}

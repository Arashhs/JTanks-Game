package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Missile extends BulletSprite {

    public Missile(Tank source) {
        super(source , (int) (source.locX + 33) , (int)(source.locY + 33) , 23 , 9 , Tile.missle);
        bulletSpeed = 10;
        damage = 100;
    }

}

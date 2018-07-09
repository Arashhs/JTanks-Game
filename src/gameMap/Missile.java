package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Missile extends BulletSprite {

    public Missile(Rectangle source , double teta) {
        super((int) (source.x + 33) , (int)(source.y + 33) , 23 , 9 , Tile.missle , teta);
        bulletSpeed = 10;
        damage = 100;
    }

}

package gameMap;

import java.awt.*;

public class LightBullet extends BulletSprite {

    public LightBullet(Rectangle source , double teta) {
        super((int) (source.x + 33) , (int)(source.y + 33) , 17 , 1 , Tile.lightBullet , teta);
        bulletSpeed = 15;
        damage = 40;
    }

}

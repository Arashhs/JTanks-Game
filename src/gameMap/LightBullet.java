package gameMap;

public class LightBullet extends BulletSprite {

    public LightBullet(Tank source) {
        super(source , (int) (source.locX + 33) , (int)(source.locY + 33) , 17 , 1 , Tile.lightBullet);
        bulletSpeed = 15;
        damage = 10;
    }

}

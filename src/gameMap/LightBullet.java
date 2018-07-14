package gameMap;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * LightBullet for machineGun
 * @author Arash
 */
public class LightBullet extends BulletSprite {

    public LightBullet(Rectangle source , double teta) {
        super((int) (source.x + 33) , (int)(source.y + 33) , 17 , 1 , Tile.lightBullet , teta);
        bulletSpeed = 15;
        damage = 40;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = Tile.lightBullet;
    }

}

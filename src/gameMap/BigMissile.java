package gameMap;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * BigMissle for BigRedGun
 * @author Arash
 */
public class BigMissile extends BulletSprite{
    public BigMissile(Rectangle source , double teta) {
        super((int) (source.x + 50) , (int)(source.y + 50) , 50 , 29 , Tile.bigMissile , teta);
        bulletSpeed = 5;
        damage = 300;
    }

    /**
     * Customized serialization
     * @param out output stream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    /**
     * Customized serialization
     * @param in input stream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = Tile.bigMissile;
    }
}

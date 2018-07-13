package gameMap;

import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BigMissile extends BulletSprite{
    public BigMissile(Rectangle source , double teta) {
        super((int) (source.x + 50) , (int)(source.y + 50) , 50 , 29 , Tile.bigMissile , teta);
        bulletSpeed = 5;
        damage = 300;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = Tile.bigMissile;
    }
}

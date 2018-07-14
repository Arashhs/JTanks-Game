package gameMap;

import bufferstrategy.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * A class that simulates the missiles
 * @author Arash
 */
public class Missile extends BulletSprite {

    public Missile(Rectangle source , double teta) {
        super((int) (source.x + 33) , (int)(source.y + 33) , 23 , 9 , Tile.missle , teta);
        bulletSpeed = 10;
        damage = 100;
    }

    /**
     * Customized serialization
     * @param out outPutStream
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    /**
     * Customized serialization
     * @param in inputStream
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = Tile.missle;
    }
}

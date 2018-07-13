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

public class Missile extends BulletSprite {

    public Missile(Rectangle source , double teta) {
        super((int) (source.x + 33) , (int)(source.y + 33) , 23 , 9 , Tile.missle , teta);
        bulletSpeed = 10;
        damage = 100;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        image = Tile.missle;
    }
}

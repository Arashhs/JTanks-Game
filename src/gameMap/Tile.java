package gameMap;

import javax.imageio.ImageIO;
import javax.swing.text.html.ImageView;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {
    public static final int tileSize = 100;
    public static int soil = 0;
    public static int wall = 1;

    public static BufferedImage tileSet_soil;
    public static BufferedImage base;
    public static BufferedImage turret1;
    public static BufferedImage turret2;

    public Tile() {
        try {
            Tile.tileSet_soil = ImageIO.read(new File("res\\soil.png"));
            base = ImageIO.read(new File("res\\tank.png"));
            turret1 = ImageIO.read(new File("res\\tankGun01.png"));
            turret2 = ImageIO.read(new File("res\\tankGun2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

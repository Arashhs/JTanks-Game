package gameMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Tile {
    public static int tileSize = 100;

    public static int soil = 0;

    public static BufferedImage tileSet_soil;

    public Tile() {
        try {
            Tile.tileSet_soil = ImageIO.read(new File("/res/soil.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

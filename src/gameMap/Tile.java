package gameMap;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Tile {
    public static final int tileSize = 64;

    public static BufferedImage tileSet_soil;
    public static BufferedImage base;
    public static BufferedImage base2;
    public static BufferedImage turret1;
    public static BufferedImage turret2;
    public static BufferedImage missle;
    public static BufferedImage lightBullet;

    public static CopyOnWriteArrayList<BufferedImage> tileImages;


    public Tile() {
        try {
            Tile.tileSet_soil = ImageIO.read(new File("res\\assets\\soil.png"));
            base = ImageIO.read(new File("res\\assets\\tank.png"));
            base2 = ImageIO.read(new File("res\\assets\\tank2.png"));
            turret1 = ImageIO.read(new File("res\\assets\\tankGun01.png"));
            turret2 = ImageIO.read(new File("res\\assets\\tankGun2.png"));
            missle = ImageIO.read(new File("res\\assets\\HeavyBullet.png"));
            lightBullet = ImageIO.read(new File("res\\assets\\LightBullet.png"));
            loadTileImages();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadTileImages() {
        // keep looking for tile A,B,C, etc. this makes it
        // easy to drop new tiles in the images/ directory
        tileImages = new CopyOnWriteArrayList<BufferedImage>();
        char ch = 'A';
        while (true) {
            String name = "tile_" + ch + ".png";
            File file = new File("res\\assets\\" + name);
            if (!file.exists()) {
                break;
            }
            tileImages.add(loadImage(name));
            ch++;
        }
    }

    public BufferedImage loadImage(String name) {
        BufferedImage image = null;
        String fileName = "res\\assets\\" + name;
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}

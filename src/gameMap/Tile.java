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
    public static BufferedImage bigEnemyBase1;
    public static BufferedImage bigEnemyBase2;
    public static BufferedImage bigEnemyTurret;
    public static BufferedImage movingEnemy2_1;
    public static BufferedImage movingEnemy2_2;
    public static BufferedImage movingEnemy2_3;
    public static BufferedImage movingEnemy2_4;
    public static BufferedImage mine;
    public static BufferedImage bigRedGun;
    public static BufferedImage bigMissile;
    public static BufferedImage blueGun;
    public static BufferedImage upgrader;
    public static BufferedImage turret1Upgraded;
    public static BufferedImage turret2Upgraded;
    public static BufferedImage repairKit;
    public static BufferedImage numberOfHeavyBullets;
    public static BufferedImage numberOfLightBullets;
    public static BufferedImage cannonAmmo;
    public static BufferedImage machineGunAmmo;

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
            bigEnemyBase1 = ImageIO.read(new File("res\\assets\\BigEnemy1.png"));
            bigEnemyBase2 = ImageIO.read(new File("res\\assets\\BigEnemy2.png"));
            bigEnemyTurret = ImageIO.read(new File("res\\assets\\BigEnemyGun.png"));
            movingEnemy2_1 = ImageIO.read(new File("res\\assets\\MovingEnemy2_1.png"));
            movingEnemy2_2 = ImageIO.read(new File("res\\assets\\MovingEnemy2_2.png"));
            movingEnemy2_3 = ImageIO.read(new File("res\\assets\\MovingEnemy2_3.png"));
            movingEnemy2_4 = ImageIO.read(new File("res\\assets\\MovingEnemy2_4.png"));
            bigRedGun = ImageIO.read(new File("res\\assets\\bigRedGun.png"));
            mine = ImageIO.read(new File("res\\assets\\mine.png"));
            bigMissile = ImageIO.read(new File("res\\assets\\BigMissile.png"));
            blueGun = ImageIO.read(new File("res\\assets\\BlueGun.png"));
            upgrader = ImageIO.read(new File("res\\assets\\upgrader.png"));
            turret1Upgraded = ImageIO.read(new File("res\\assets\\turret1Upgraded.png"));
            turret2Upgraded = ImageIO.read(new File("res\\assets\\turret2Upgraded.png"));
            repairKit = ImageIO.read(new File("res\\assets\\repairKit.png"));
            numberOfHeavyBullets = ImageIO.read(new File("res\\assets\\NumberOfHeavyBullet2.png"));
            numberOfLightBullets = ImageIO.read(new File("res\\assets\\NumberOfMachinGun2.png"));
            cannonAmmo = ImageIO.read(new File("res\\assets\\CannonFood2.png"));
            machineGunAmmo = ImageIO.read(new File("res\\assets\\MachinGunFood2.png"));
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

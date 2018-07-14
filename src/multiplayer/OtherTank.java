package multiplayer;

import bufferstrategy.GameState;
import bufferstrategy.Main;
import gameMap.BulletSprite;
import gameMap.Tank;
import gameMap.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class simulates the other player's tank
 * in a multiplayer session
 * This extends from the Tank class
 * @author Arash
 */
public class OtherTank extends Tank {
    private int weapstate;
    private BufferedImage thisImage;
    private double otherAngle;

    public OtherTank(int cx, int cy, int turretAngle, int weaponSelected, CopyOnWriteArrayList<BulletSprite> bullets) {
        locX = cx;
        locY = cy;
        otherAngle = turretAngle;
        weapstate = weaponSelected;
        setBulletSprites(new CopyOnWriteArrayList<>());
        thisImage = Tile.base;
        turretImage = Tile.turret1;
        image = Tile.turret1;
    }

    /**
     * Renders and shows the other player's tank in a MP session
     * @param g2d Graphic's object
     * @param state Current game state
     */
    public synchronized void render(Graphics2D g2d, GameState state) {
        if (isVertical)
            thisImage = Tile.base2;
        else
            thisImage = Tile.base;
        if (gunState == 0) {
            if (isCannonUpgraded())
                image = Tile.turret1Upgraded;
            else
                image = Tile.turret1;
        } else {
            if (isMachinGunUpgraded())
                image = Tile.turret2Upgraded;
            else
                image = Tile.turret2;
        }
        if (hp > 0) {
            g2d.drawImage(thisImage, locX - state.sX, locY - state.sY, null);
            backupAt = g2d.getTransform();
            at = new AffineTransform();
            at.rotate(otherAngle, locX - state.sX + diam / 2 + 33, locY - state.sY + diam / 2 + 30);
            g2d.setTransform(at);
            g2d.drawImage(image, locX + diam / 2 - state.sX, locY + diam / 2 - state.sY, null);

            g2d.setTransform(backupAt);
            for (int i = 0; i < getBulletSprites().size(); i++) {
                if (getBulletSprites().get(i).isCollided()) {
                    getBulletSprites().remove(i);
                    continue;
                }
                getBulletSprites().get(i).move(g2d);
            }
        }
        else {
            GameState.lastEvent = "Other player died!";
            Main.gameMode = 0;
            Main.connectionType = -1;
        }
    }

    /* **************Getter and Setters************ */
    public int getWeapstate() {
        return weapstate;
    }

    public void setWeapstate(int weapstate) {
        this.weapstate = weapstate;
    }

    public double getOtherAngle() {
        return otherAngle;
    }

    public void setOtherAngle(double otherAngle) {
        this.otherAngle = otherAngle;
    }
    /* **************Getter and Setters************ */
}

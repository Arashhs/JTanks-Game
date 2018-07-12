package multiplayer;

import bufferstrategy.GameState;
import gameMap.BulletSprite;
import gameMap.Tank;
import gameMap.Tile;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;

public class OtherTank extends Tank {
    private int weapstate;
    private BufferedImage thisImage;
    public OtherTank(int cx , int cy , int turretAngle , int weaponSelected, CopyOnWriteArrayList<BulletSprite> bullets){
        locX = cx;
        locY = cy;
        this.angle = turretAngle;
        weapstate = weaponSelected;
        setBulletSprites(bullets);
        thisImage = Tile.base;
        turretImage = Tile.turret1;
        image = Tile.turret1;
    }

    public synchronized void render(Graphics2D g2d, GameState state) {
        g2d.drawImage(thisImage, locX - state.sX, locY - state.sY, null);

        backupAt = g2d.getTransform();
        at = new AffineTransform();
        at.rotate(angle, locX - state.sX + diam/2 + 33 ,locY - state.sY + diam/2 + 30 );
        g2d.setTransform(at);
        g2d.drawImage(image , locX + diam/2 - state.sX  , locY + diam/2 - state.sY ,null);

        g2d.setTransform(backupAt);
/*        for (int i = 0; i < getBulletSprites().size(); i++) {
            if (getBulletSprites().get(i).isCollided()) {
                getBulletSprites().remove(i);
                continue;
            }
            getBulletSprites().get(i).move(g2d);
        } */
    }

    public int getWeapstate() {
        return weapstate;
    }

    public void setWeapstate(int weapstate) {
        this.weapstate = weapstate;
    }


}

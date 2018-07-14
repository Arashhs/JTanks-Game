package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

/**
 * MachineGun ammo pickUp for the player
 * @author Arash
 */
public class MachineGunAmmo extends PickUp {

    public MachineGunAmmo(int x, int y) {
        super(x, y, 55, 50, Tile.machineGunAmmo);
    }

    /**
     * Increase the number of bullets of player after picking up
     * @param gState GameState item
     * @param g2d Graphics object
     */
    public void tick(GameState gState, Graphics2D g2d) {
        GameState.lastEvent = "Added machine gun ammo!";
        GameState.tank.increaseMachinGunAmmo();
    }
}

package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

public class MachineGunAmmo extends PickUp {

    public MachineGunAmmo(int x, int y) {
        super(x, y, 55, 50, Tile.machineGunAmmo);
    }

    public void tick(GameState gState, Graphics2D g2d) {
        GameState.lastEvent = "Added machine gun ammo!";
        GameState.tank.increaseMachinGunAmmo();
    }
}

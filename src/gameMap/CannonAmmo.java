package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

public class CannonAmmo extends PickUp {

    public CannonAmmo(int x, int y) {
        super(x, y, 55, 45, Tile.cannonAmmo);
    }

    public void tick(GameState gState, Graphics2D g2d) {
        GameState.lastEvent = "Added cannon ammo!";
        GameState.tank.increaseMissile();
    }
}

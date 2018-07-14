package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

/**
 * CannonAmmo pickUp which increases number of cannons for the player
 */
public class CannonAmmo extends PickUp {

    public CannonAmmo(int x, int y) {
        super(x, y, 55, 45, Tile.cannonAmmo);
    }

    /**
     * Act when is picked up
     * @param gState Gamestate for player
     * @param g2d Graphics object for the main frame... Useless for now but it is needed for the future updates
     */
    public void tick(GameState gState, Graphics2D g2d) {
        GameState.lastEvent = "Added cannon ammo!";
        GameState.tank.increaseMissile();
    }
}

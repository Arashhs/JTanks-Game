package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

/**
 * This is a pickUp item
 * This repairs player's tank
 * @author Arash
 */
public class RepairKit extends PickUp {
    public RepairKit(int x, int y) {
        super(x, y, 65, 45, Tile.repairKit);
    }

    /**
     * Act!
     * @param gState GameState of the game
     * @param g2d Graphics object of main game frame
     */
    public void tick(GameState gState, Graphics2D g2d) {
        GameState.lastEvent = "Tank Repaired!";
        GameState.tank.setHp(Tank.MAX_HP);
    }
}

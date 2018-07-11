package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

public class RepairKit extends PickUp {
    public RepairKit(int x, int y) {
        super(x, y, 65, 45, Tile.repairKit);
    }

    public void tick(GameState gState, Graphics2D g2d) {
        GameState.lastEvent = "Tank Repaired!";
        GameState.tank.setHp(Tank.MAX_HP);
    }
}

package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class Loot {

    private CopyOnWriteArrayList<PickUp> loots;

    public Loot() {
        loots = new CopyOnWriteArrayList<>();
    }

    public Point position(int tileWidth, int tileHeight) {
        return new Point(tileWidth * Tile.tileSize, tileHeight * Tile.tileSize);
    }

    public void addPickUp(char type, int tileWidth, int tileHeight) {
        Point p = position(tileWidth, tileHeight);
        if (type == '^') {
            loots.add(new WeaponUpgrader(p.x, p.y));
        }
    }


    public void render(Graphics2D g2d, GameState state) {
        for (PickUp pickUp : loots) {
            pickUp.render(g2d, state);
        }
    }

    public CopyOnWriteArrayList<PickUp> getLoots() {
        return loots;
    }
}
package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This class holds the data for all the pick-up items in the game
 * @author Arash
 */
public class Loot {

    private CopyOnWriteArrayList<PickUp> loots; //arraylist for all pickUp items

    public Loot() {
        loots = new CopyOnWriteArrayList<>();
    }

    /**
     * Returns a coordinate based on tile number
     * @param tileWidth tile's width
     * @param tileHeight tile's height
     * @return tile coordinate
     */
    public Point position(int tileWidth, int tileHeight) {
        return new Point(tileWidth * Tile.tileSize, tileHeight * Tile.tileSize);
    }

    /**
     * Adds a new pickUp item based on id on map
     * @param type id for the pickUp
     * @param tileWidth tile's width
     * @param tileHeight tile's height
     */
    public void addPickUp(char type, int tileWidth, int tileHeight) {
        Point p = position(tileWidth, tileHeight);
        if (type == '^') {
            loots.add(new WeaponUpgrader(p.x, p.y));
        }
        else if (type == '&') {
            loots.add(new RepairKit(p.x, p.y));
        }
        else if (type == '*') {
            loots.add(new CannonAmmo(p.x, p.y));
        }
        else if (type == '(') {
            loots.add(new MachineGunAmmo(p.x, p.y));
        }
    }

    /**
     * Manually add a pickUp without using text-id (Above method)
     * This is used for the death-loot of enemies
     * @param pickUp
     */
    public void addManual(PickUp pickUp){
        loots.add(pickUp);
    }


    /**
     * Renders and displays all the pickUp items
     * @param g2d graphic's object
     * @param state Current GameState
     */
    public void render(Graphics2D g2d, GameState state) {
        for (PickUp pickUp : loots) {
            pickUp.render(g2d, state);
        }
    }

    /**
     *
     * @return Array of all pickUp items
     */
    public CopyOnWriteArrayList<PickUp> getLoots() {
        return loots;
    }

    /**
     * There is chance for each enemy to drop a loot on death
     * This calculates and spawns the possible loot
     * @param xLocation x coordinate of the dead enemy
     * @param yLocation y coordinate of the dead enemy
     */
    public void deathLoot(int xLocation , int yLocation){
        int type = (new Random()).nextInt(4);
        int val = (new Random()).nextInt(100);
        PickUp p = null;
        if(type == 0 && val < PickUp.AMMO_RARITY){
            p = new CannonAmmo(xLocation , yLocation);
            GameState.loots.addManual(p);
        }

        else if(type == 1 && val < PickUp.AMMO_RARITY){
            p = new MachineGunAmmo(xLocation , yLocation);
            GameState.loots.addManual(p);
        }
        else if(type == 2 && val < PickUp.REPAIR_KIT_RARITY){
            p = new RepairKit(xLocation , yLocation);
            GameState.loots.addManual(p);
        }
        else if(type == 3 && val < PickUp.UPGRADER_RARITY){
            p = new WeaponUpgrader(xLocation , yLocation);
            GameState.loots.addManual(p);
        }
    }
}
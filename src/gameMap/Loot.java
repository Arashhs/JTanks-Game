package gameMap;

import bufferstrategy.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
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

    public void addManual(PickUp pickUp){
        loots.add(pickUp);
    }


    public void render(Graphics2D g2d, GameState state) {
        for (PickUp pickUp : loots) {
            pickUp.render(g2d, state);
        }
    }

    public CopyOnWriteArrayList<PickUp> getLoots() {
        return loots;
    }

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
        System.out.println("Type: " + type);
        System.out.println("Val: " + val);
    }
}
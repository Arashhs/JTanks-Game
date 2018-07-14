package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

/**
 * This is the WeaponUpgrader class which increases damage of the player's selected gun
 */
public class WeaponUpgrader extends PickUp {
    public WeaponUpgrader(int x , int y){
        super(x , y , 55 , 55 , Tile.upgrader);
    }

    /**
     * updates and powers up player's using weapon
     * @param gState GameState of the game
     * @param g2d Graphics object of main game frame
     */
    public void tick(GameState gState , Graphics2D g2d){
        if(gState.getGunState() == 0){
            Tank.tankMissileDamage += 100;
            Tank.setTurret1(Tile.turret1Upgraded);
            gState.getTank().setCannonUpgraded(true);
            GameState.lastEvent = "Cannon's damage Upgraded!";
        }
        else if (gState.getGunState() == 1){
            Tank.tankBulletDamage += 40;
            Tank.setTurret2(Tile.turret2Upgraded);
            gState.getTank().setMachinGunUpgraded(true);
            GameState.lastEvent = "Bullet's damage Upgraded!";
        }
    }

}

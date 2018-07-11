package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

public class WeaponUpgrader extends PickUp {
    public WeaponUpgrader(int x , int y){
        super(x , y , 55 , 55 , Tile.upgrader);
    }

    public void tick(GameState gState , Graphics2D g2d){
        if(gState.getGunState() == 0){
            Tank.tankMissileDamage += 100;
            Tank.setTurret1(Tile.turret1Upgraded);
            GameState.lastEvent = "Cannon's damage Upgraded!";
        }
        else if (gState.getGunState() == 1){
            Tank.tankBulletDamage += 40;
            Tank.setTurret2(Tile.turret2Upgraded);
            GameState.lastEvent = "Bullet's damage Upgraded!";
        }
    }

}

package gameMap;

import bufferstrategy.GameState;

import java.awt.*;

public class WeaponUpgrader extends PickUp {
    public WeaponUpgrader(int x , int y){
        super(x , y , 55 , 55 , Tile.upgrader);
    }

    public void tick(GameState gState , Graphics2D g2d){
        if(gState.getGunState() == 0){
            System.out.println("Canon Upgrade");
        }
        else if (gState.getGunState() == 1){
            System.out.println("Bullet Upgrade");
        }
    }

}

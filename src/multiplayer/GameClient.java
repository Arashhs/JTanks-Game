package multiplayer;

import bufferstrategy.GameLoop;
import bufferstrategy.GameState;
import bufferstrategy.Main;
import gameMap.*;

import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This is the client's class
 * This reads data from server and updates client's game
 * This also sends the state of client's tank and other necessary datas to the server
 * @author Arash
 */
public class GameClient implements Runnable {
    private Socket connection;
    private ObjectInputStream networkInputStream;
    private ObjectOutputStream networkOutputStream;
    private String serverAddress;
    private  boolean interruptThis;

    private boolean connectionStatus;

    public static OtherTank otherTank;

    public GameClient() {
        connectionStatus = false;
        serverAddress = JOptionPane.showInputDialog("Enter IP Address of a machine that is\n" + "running the date service on port 50000:");
        while (!connectionStatus) {
            try {
                connection = new Socket(serverAddress, 50000);
                System.out.println("Client connected");
                connectionStatus = true;
            } catch (IOException e) {
                e.printStackTrace();
                serverAddress = JOptionPane.showInputDialog("We couldn't find selected IP Adress\nEnter a valid IP Address of a machine that is\n" + "running the date service on port 50000:");
            }
        }
        try {
            networkOutputStream = new ObjectOutputStream(connection.getOutputStream());
            networkInputStream = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e){
            e.printStackTrace();
        }
        otherTank = new OtherTank(500 , 600 , 0 , 0 , null);
        interruptThis = false;
    }

    public void run(){
        int i = 0;
        while (!interruptThis) {
            i++;
            if(i>10) {
                tick();
                i = 11;
            } /*
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } */

        }
    }

    /**
     * Syncs data between server and client
     */
    public void tick() {
        Tank ta;
        Level lvl;
        Enemies enemies;
        GameState gameState;

            try {
                networkOutputStream.writeObject(GameLoop.getState().getTank());
                networkOutputStream.reset();
                gameState = (GameState) networkInputStream.readObject();
                ta = gameState.getTankForNet();
            //    lvl = gameState.getLevelForNet();
                enemies = gameState.getEnemiesForNet();
                initOtherTank(ta);
            //    GameState.setLevel(lvl);
                GameState.setEnemies(enemies);

            } catch (IOException e) {
                System.out.println("Server Left the Game");
                GameState.lastEvent = "Server left the game!";
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    /**
     * updates the other player's tank (= Server player's tank) in client's game
     * @param ta otherPlayer's tank
     */
    public void initOtherTank(Tank ta){
        otherTank.setBulletSprites(ta.getBulletSprites());
        otherTank.setOtherAngle(ta.getTurretAngle());
        otherTank.locX = ta.locX;
        otherTank.locY = ta.locY;
        otherTank.setVertical(ta.isVertical());
        otherTank.setGunState(ta.getGunState());
        otherTank.setMachinGunUpgraded(ta.isMachinGunUpgraded());
        otherTank.setCannonUpgraded(ta.isCannonUpgraded());
        otherTank.setHp(ta.getHp());
        if(ta.getHp() <= 0){
            Main.gameMode = 0;
            Main.connectionType = -1;
            interruptThis = true;
        }
    }

}

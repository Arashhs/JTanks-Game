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

public class GameClient implements Runnable {
    private Socket connection;
    private ObjectInputStream networkInputStream;
    private ObjectOutputStream networkOutputStream;
    private String serverAddress;

    public static OtherTank otherTank;

    public GameClient() {
        serverAddress = JOptionPane.showInputDialog("Enter IP Address of a machine that is\n" + "running the date service on port 50000:");
        try {
            connection = new Socket(serverAddress, 50000);
            networkOutputStream = new ObjectOutputStream(connection.getOutputStream());
            networkInputStream = new ObjectInputStream(connection.getInputStream());
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
            serverAddress = JOptionPane.showInputDialog("We couldn't find selected IP Adress\nEnter a valid IP Address of a machine that is\n" + "running the date service on port 50000:");
        }
        otherTank = new OtherTank(500 , 600 , 0 , 0 , null);

    }

    public void run(){
        int i = 0;
        while (true) {
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

    public void tick() {
        Tank ta;
        Level lvl;
        Enemies enemies;

            try {
                networkOutputStream.writeObject(GameLoop.getState().getTank());
                networkOutputStream.reset();
                ta = (Tank) networkInputStream.readObject();
                initOtherTank(ta);

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
    }

    public void initOtherTank(Tank ta){
        otherTank.setBulletSprites(ta.getBulletSprites());
        otherTank.setHp(ta.getHp());
        otherTank.setOtherAngle(ta.getTurretAngle());
        otherTank.locX = ta.locX;
        otherTank.locY = ta.locY;
        otherTank.setVertical(ta.isVertical());
        otherTank.setGunState(ta.getGunState());
    }

}

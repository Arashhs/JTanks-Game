package multiplayer;

import bufferstrategy.GameLoop;
import bufferstrategy.Main;
import gameMap.Level;
import gameMap.Tank;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GameServer implements Runnable{

    private ServerSocket serverSocket;
    private int port;
    private Socket connection;
    private ObjectInputStream networkInputStream;
    private ObjectOutputStream networkOutputStream;

    private boolean connected;

    public static OtherTank otherTank;

    public GameServer() {
        connection = null;
        otherTank = new OtherTank(500 , 600 , 0 , 0 , null);
    }

    public GameServer(int port) {
        try {
            this.serverSocket = new ServerSocket(50000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.port = port;
        connection = null;
        connected = false;
    }

    @Override
    public void run() {
        connect();
        while (true){
            tick();/*
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } */
        }
    }

    public void connect(){
        try {
            serverSocket = new ServerSocket(50000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        port = 50000;
            try {
                System.out.println("Waiting for Connection...");
                connection = serverSocket.accept();
                networkOutputStream = new ObjectOutputStream(connection.getOutputStream());
                networkOutputStream.flush();
                networkInputStream = new ObjectInputStream(connection.getInputStream());
                connected = true;
                System.out.println("Connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public void tick(){
        if (connected){
            Tank ta;
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
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public void setServerSocket(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Socket getConnection() {
        return connection;
    }

    public void setConnection(Socket connection) {
        this.connection = connection;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public void initOtherTank(Tank ta){
        try {
            otherTank.setBulletSprites(ta.getBulletSprites());
            otherTank.setHp(ta.getHp());
            otherTank.setOtherAngle(ta.getTurretAngle());
            otherTank.locX = ta.locX;
            otherTank.locY = ta.locY;
            otherTank.setVertical(ta.isVertical());
            otherTank.setGunState(ta.getGunState());
            otherTank.setMachinGunUpgraded(ta.isMachinGunUpgraded());
            otherTank.setCannonUpgraded(ta.isCannonUpgraded());
        }
        catch (NullPointerException e){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }

}

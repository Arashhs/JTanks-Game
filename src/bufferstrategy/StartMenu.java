package bufferstrategy;

import gameMap.Tile;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Start menu for the game
 * All data related to connectionType and networkType is initialized here
 * @author Arash Hajisafi
 */
public class StartMenu extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = 1L;

    int width, height;

    JButton singleplayer = new JButton("play singleplayer");
    JButton multiplayer = new JButton("play multiplayer");
    JButton exit = new JButton("exit");
    JButton mainMenu = new JButton("main menu");
    JButton mainMenu2 = new JButton("main menu");
    JButton mainMenu3 = new JButton("main menu");
    JButton easyMode = new JButton("Easy");
    JButton mediumMode = new JButton("Medium");
    JButton hardMode = new JButton("Hard");
    JButton howToPlay = new JButton("How to play");
    JButton host = new JButton("Host");
    JButton client = new JButton("Client");

    CardLayout layout = new CardLayout();

    JPanel panel = new JPanel();
    JPanel game = new JPanel();
    JPanel menu = new JPanel();
    JPanel htp = new JPanel();
    JPanel mp = new JPanel();


    public StartMenu(int width, int height) {
        this.width = width;
        this.height = height;

        panel.setLayout(layout);
        addButtons();

        setSize(width, height);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
        setTitle("JTanks by Arash Hajisafi");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        requestFocus();

    }

    /**
     * Add buttons
     */
    private void addButtons() {

        singleplayer.addActionListener(this);
        multiplayer.addActionListener(this);
        exit.addActionListener(this);
        mainMenu.addActionListener(this);
        mainMenu2.addActionListener(this);
        mainMenu3.addActionListener(this);
        easyMode.addActionListener(this);
        mediumMode.addActionListener(this);
        hardMode.addActionListener(this);
        howToPlay.addActionListener(this);
        host.addActionListener(this);
        client.addActionListener(this);

        //menu buttons
        menu.add(singleplayer);
        menu.add(multiplayer);
        menu.add(howToPlay);
        menu.add(exit);
        menu.add(new JLabel(new ImageIcon("res\\assets\\startMenu.png")));

        //game buttons
        game.add(mainMenu);
        game.add(easyMode);
        game.add(mediumMode);
        game.add(hardMode);
        game.add(new JLabel(new ImageIcon("res\\assets\\startMenu.png")));


        //How to play Buttons
        htp.add(mainMenu2);
        htp.add(new JLabel(new ImageIcon("res\\assets\\howToPlay.png")));

        //Multiplayer buttons
        mp.add(mainMenu3);
        mp.add(host);
        mp.add(client);
        mp.add(new JLabel(new ImageIcon("res\\assets\\startMenu.png")));

        //background colors
        game.setBackground(new Color(69, 89 , 188));
        menu.setBackground(new Color(69, 89 , 188));
        htp.setBackground(new Color(69,89,188));
        mp.setBackground(new Color(69, 89, 188));


        //adding children to parent Panel
        panel.add(menu,"Menu");
        panel.add(game,"Game");
        panel.add(htp , "htp");
        panel.add(mp , "mp");

        add(panel);
        layout.show(panel,"Menu");


    }

    /**
     * Add listeners
     * @param event event parameter
     */
    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == singleplayer) {
            Main.gameMode = 0;
            Main.connectionType = -1;
            layout.show(panel, "Game");
        } else if (source == multiplayer){
            Main.gameMode = 1;
            layout.show(panel, "mp");
        } else if (source == mainMenu || source == mainMenu2 || source == mainMenu3){
            layout.show(panel, "Menu");
        } else if (source == easyMode){
            Main.gameDifficulty = 1;
        } else if (source == mediumMode){
            Main.gameDifficulty = 2;
        } else if (source == hardMode){
            Main.gameDifficulty = 3;
        } else if (source == howToPlay){
            layout.show(panel, "htp");
        } else if(source == host){
            Main.connectionType = 0;
            layout.show(panel , "Game");
        } else if(source == client){
            Main.gameDifficulty = 1;
            Main.connectionType = 1;
        }

    }



        @Override
    public void run() {
        this.setVisible(true);
        while (Main.gameDifficulty == 0 && Main.connectionType != 1){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dispose();
    }
}
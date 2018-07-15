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
    JButton mainMenu4 = new JButton("main menu");
    JButton easyMode = new JButton("Easy");
    JButton mediumMode = new JButton("Medium");
    JButton hardMode = new JButton("Hard");
    JButton howToPlay = new JButton("How to play");
    JButton host = new JButton("Host");
    JButton client = new JButton("Client");
    JButton mapEditor = new JButton("Map Editor");
    JButton level1 = new JButton("Level 1");
    JButton level2 = new JButton("level 2");
    JButton levelEdited = new JButton("Edited level");

    CardLayout layout = new CardLayout();

    JPanel panel = new JPanel();
    JPanel game = new JPanel();
    JPanel menu = new JPanel();
    JPanel htp = new JPanel();
    JPanel mp = new JPanel();
    JPanel gameSelection = new JPanel();


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
        mainMenu4.addActionListener(this);
        easyMode.addActionListener(this);
        mediumMode.addActionListener(this);
        hardMode.addActionListener(this);
        howToPlay.addActionListener(this);
        host.addActionListener(this);
        client.addActionListener(this);
        mapEditor.addActionListener(this);
        level1.addActionListener(this);
        level2.addActionListener(this);
        levelEdited.addActionListener(this);

        //menu buttons
        menu.add(singleplayer);
        menu.add(multiplayer);
        menu.add(mapEditor);
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

        //gameSelection buttons
        gameSelection.add(mainMenu4);
        gameSelection.add(level1);
        gameSelection.add(level2);
        gameSelection.add(levelEdited);
        gameSelection.add(new JLabel(new ImageIcon("res\\assets\\startMenu.png")));

        //background colors
        game.setBackground(new Color(69, 89 , 188));
        menu.setBackground(new Color(69, 89 , 188));
        htp.setBackground(new Color(69,89,188));
        mp.setBackground(new Color(69, 89, 188));
        gameSelection.setBackground(new Color(69,89,188));


        //adding children to parent Panel
        panel.add(menu,"Menu");
        panel.add(game,"Game");
        panel.add(htp , "htp");
        panel.add(mp , "mp");
        panel.add(gameSelection, "GameSelection");

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
            layout.show(panel, "GameSelection");
        } else if (source == multiplayer){
            Main.gameMode = 1;
            Main.levelSelection = 1;
            layout.show(panel, "mp");
        } else if (source == mainMenu || source == mainMenu2 || source == mainMenu3 || source == mainMenu4){
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
            Main.levelSelection = 1;
            layout.show(panel , "Game");
        } else if(source == client){
            Main.gameDifficulty = 1;
            Main.connectionType = 1;
            Main.levelSelection = 1;
        } else if(source == mapEditor){
            try {
                Desktop.getDesktop().open(new File("res\\maps\\map_edited.txt"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if(source == level1){
            Main.gameMode = 0;
            Main.connectionType = -1;
            Main.levelSelection = 1;
            layout.show(panel, "Game");
        } else if(source == level2){
            Main.gameMode = 0;
            Main.connectionType = -1;
            Main.levelSelection = 2;
            layout.show(panel, "Game");
        } else if(source == levelEdited) {
            Main.gameMode = 0;
            Main.connectionType = -1;
            Main.levelSelection = 0;
            Main.gameDifficulty = 2;
        }

    }



        @Override
    public void run() {
        this.setVisible(true);
        while ((Main.gameDifficulty == 0 && Main.connectionType != 1) && Main.levelSelection != 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        dispose();
    }
}
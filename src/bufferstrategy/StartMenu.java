package bufferstrategy;

import gameMap.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu extends JFrame implements ActionListener, Runnable {
    private static final long serialVersionUID = 1L;

    int width, height;

    JButton singleplayer = new JButton("play singleplayer");
    JButton multiplayer = new JButton("play multiplayer");
    JButton exit = new JButton("exit");
    JButton mainMenu = new JButton("main menu");
    JButton easyMode = new JButton("Easy");
    JButton mediumMode = new JButton("Medium");
    JButton hardMode = new JButton("Hard");

    CardLayout layout = new CardLayout();

    JPanel panel = new JPanel();
    JPanel game = new JPanel();
    JPanel menu = new JPanel();

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

    private void addButtons() {

        singleplayer.addActionListener(this);
        multiplayer.addActionListener(this);
        exit.addActionListener(this);
        mainMenu.addActionListener(this);
        easyMode.addActionListener(this);
        mediumMode.addActionListener(this);
        hardMode.addActionListener(this);

        //menu buttons
        menu.add(singleplayer);
        menu.add(multiplayer);
        menu.add(exit);

        //game buttons
        game.add(mainMenu);
        game.add(easyMode);
        game.add(mediumMode);
        game.add(hardMode);

        //background colors
        game.setBackground(new Color(200, 188 , 171));
        menu.setBackground(new Color(192, 212 , 170));

        //adding children to parent Panel
        panel.add(menu,"Menu");
        panel.add(game,"Game");

        add(panel);
        layout.show(panel,"Menu");

    }

    public void actionPerformed(ActionEvent event) {

        Object source = event.getSource();

        if (source == exit) {
            System.exit(0);
        } else if (source == singleplayer) {
            Main.gameMode = 0;
            layout.show(panel, "Game");
        } else if (source == multiplayer){
            Main.gameMode = 1;
            layout.show(panel, "Game");
        } else if (source == mainMenu){
            layout.show(panel, "Menu");
        } else if (source == easyMode){
            Main.gameDifficulty = 1;
        } else if (source == mediumMode){
            Main.gameDifficulty = 2;

        } else if (source == hardMode){
            Main.gameDifficulty = 3;
        }

    }

    @Override
    public void run() {
        StartMenu sc = new StartMenu(1280, 720);
        sc.setVisible(true);
        while (Main.gameDifficulty == 0){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
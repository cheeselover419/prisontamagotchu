package com.example;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionListener;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.text.MessageFormat;
import java.awt.MenuItem;

import java.util.ResourceBundle;

public class CanvasLayer implements ActionListener {
    public Pet name;

    GameLogic gl = new GameLogic();
    JFrame mainFrame = new JFrame("Prison Tamagotchu");

    Canvas canvas = new Canvas();
    ArrayList<Food> foodCanvas;
    ArrayList<Games> gamesCanvas;
    Graphics g;
    int width = 615;
    int height = 635;
    BufferStrategy bufferStrategy;
    private MenuItem newGame;
    private MenuItem continueGame;
    private MenuItem engLang;
    private MenuItem rusLang;
    private MenuItem exit;
    private MenuItem restart;

    BufferedImage cupcake, pizza, broccoli, bg, cutlery, home, games, cursor, radio;

    private ResourceBundle messages;
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "New":
                GameLogic.startNewGame(name, this);
                break;
            case "Continue":
                // Handle Continue action
                break;
            case "Restart":
                GameLogic.restartGame(name, this);
                break;
            case "Exit":
                // Handle Exit action
                System.exit(0);
                break;
            case "eng":
                messages = ResourceBundle.getBundle("Messages", Locale.ENGLISH);
                updateUI();
                break;
            case "rus":
                messages = ResourceBundle.getBundle("Messages", new Locale("ru", "RU"));
                updateUI();
                break;
            // Add more cases as needed
        }
    }

    public CanvasLayer(ArrayList<Food> foodCanvas, ArrayList<Games> gamesCanvas, Pet name) {
        this.foodCanvas = foodCanvas;
        this.gamesCanvas = gamesCanvas;
        this.name = name;

        MenuBar mb = new MenuBar();
        mainFrame.setMenuBar(mb);
        messages = ResourceBundle.getBundle("Messages", Locale.getDefault());


        Menu menu = new Menu(messages.getString("fileMenuLabel"));

        newGame = new MenuItem(messages.getString("newGameMenuItemLabel"));
        newGame.setActionCommand("New");
        newGame.addActionListener(this);

        continueGame = new MenuItem(messages.getString("continueMenuItemLabel"));
        continueGame.setActionCommand("Continue");
        continueGame.addActionListener(this);

        restart = new MenuItem(messages.getString("restartMenuItemLabel"));
        restart.setActionCommand("Restart");
        restart.addActionListener(this);

        exit = new MenuItem(messages.getString("exitMenuItemLabel"));
        exit.setActionCommand("Exit");
        exit.addActionListener(this);

        Menu language = new Menu(messages.getString("languageMenuLabel"));
        engLang = new MenuItem(messages.getString("englishMenuItemLabel"));
        engLang.setActionCommand("eng");
        engLang.addActionListener(this);

        rusLang = new MenuItem(messages.getString("russianMenuItemLabel"));
        rusLang.setActionCommand("rus");
        rusLang.addActionListener(this);

        language.add(engLang);
        language.add(rusLang);
        menu.add(newGame);
        menu.add(continueGame);
        menu.add(restart);
        menu.addSeparator();
        menu.add(language);
        menu.addSeparator();
        menu.add(exit);

        mb.add(menu);

//        filepath to non animated images (food & background)
        try {
            bg = ImageIO.read(new File("src/images/bg.png"));
            home = ImageIO.read(new File("src/images/home.png"));
            games = ImageIO.read(new File("src/images/gamepad.png"));
            cursor = ImageIO.read(new File("src/images/gamepad.png"));
            radio = ImageIO.read(new File("src/images/gamepad.png"));

            cutlery = ImageIO.read(new File("src/images/cutlery.png"));

            cupcake = ImageIO.read(new File("src/images/sigarette.png"));
            pizza = ImageIO.read(new File("src/images/pizza.png"));
            broccoli = ImageIO.read(new File("src/images/broccoli.png"));
            //restart = ImageIO.read(new File("src/images/restart.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

//        setting up the frame and canvas
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(width, height);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);

        canvas.setSize(width, height);
        //canvas.setBackground(new Color(126, 146, 203, 255));
        canvas.setVisible(true);

        mainFrame.add(canvas);
        canvas.createBufferStrategy(3);
        bufferStrategy = canvas.getBufferStrategy();
    }

    public void basicLayer() {

//button dummies
//        MenuButtons homeB = new MenuButtons();
//        homeB.homeParameter();
//        MenuButtons cutlB = new MenuButtons();
//        cutlB.restartParameter();

        g = bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, width, height);
        g.drawImage(bg, 0, 0, null);
        g.drawImage(home, 0, 0, null);
        g.drawImage(games, 150,435, null);
        g.drawImage(cutlery, 0, 0, null);
        g.setColor(new Color(0, 0, 0));
//        g.drawRect(homeB.x, homeB.y, homeB.width, homeB.height);
//        g.drawRect(cutlB.x, cutlB.y, cutlB.width, cutlB.height);
    }

    public void petStats() {
        g.setColor(new Color(255, 255, 255));
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString(messages.getString("nameLabel") + " " + name.name, 290, 100);

        g.setColor(new Color(0, 0, 0, 119));
        g.drawString("Healths", 291, 121);
        for (int i = 1; i <= name.healthLvl / 10; i++) {
            int rectPos = 351 + i * 11;
            g.drawRect(rectPos, 111, 8, 10);
        }
        g.drawString("Happiness", 286, 141);
        for (int i = 1; i <= name.happinessLvl / 10; i++) {
            int rectPos = 351 + i * 11;
            g.drawRect(rectPos, 131, 8, 10);
        }
        g.setColor(new Color(0, 255, 69, 255));
        g.drawString(messages.getString("healthLabel"), 290, 120);
        for (int i = 1; i <= name.healthLvl / 10; i++) {
            int rectPos = 350 + i * 11;
            g.drawRect(rectPos, 110, 8, 10);
        }
        g.setColor(new Color(255, 221, 0));
        g.drawString(messages.getString("happinessLabel"), 285, 140);
        for (int i = 1; i <= name.happinessLvl / 10; i++) {
            int rectPos = 350 + i * 11;
            g.drawRect(rectPos, 130, 8, 10);
        }
        g.setColor(new Color(255,0,0));
        g.drawString(messages.getString("moneyLabel"), 290, 160);
        g.drawString(String.valueOf(name.money), 370, 160);
        try {
            if (name.loveLvl < 100) {
                g.drawImage(ImageIO.read(new File(Heart.risingHeart((name.loveLvl) / 5))), 102, 90, null);
            } else {
                g.drawImage(ImageIO.read(new File(Heart.risingHeart(19))), 102, 90, null);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void petAnimation() {
        try {
            g.drawImage(ImageIO.read(new File(name.filePet)), 0, 80, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mainMenu(){
        g.setColor(new Color(255,255,0));
        g.drawString("Main Menu", width / 2, height / 2 - 80);
    }

    public void feeding() {
        g.setColor(new Color(255, 255, 0));
        g.drawString(messages.getString("feedPrompt"), width / 2 - 70, height / 2 - 80);

        for (Food i : foodCanvas) {
            if (i == foodCanvas.get(0)) {
                g.drawString(messages.getString("cigarette"), i.x + 30, i.y + 100);
                g.drawImage(cupcake, i.x, i.y, null);
                g.drawString("10 $", i.x + 40, i.y + 120);
            }
            if (i == foodCanvas.get(1)) {
                g.drawString(messages.getString("pizza"), i.x + 40, i.y + 100);
                g.drawImage(pizza, i.x, i.y, null);
                g.drawString("30 $", i.x + 40, i.y + 120);
            }
            if (i == foodCanvas.get(2)) {
                g.drawString(messages.getString("broccoli"), i.x + 30, i.y + 100);
                g.drawImage(broccoli, i.x, i.y, null);
                g.drawString("30 $", i.x + 40, i.y + 120);
            }
        }
    }

    public void games() {
        g.setColor(new Color(255, 255, 0));
        g.drawString(messages.getString("gamesPrompt"), width / 2 - 70, height / 2 - 80);

        for (Games i : gamesCanvas) {
            if (i == gamesCanvas.get(0)) {
                g.drawString(messages.getString("clickerGame"), i.x + 30, i.y + 100);
                g.drawImage(cursor, i.x, i.y, null);
            }

            if (i == gamesCanvas.get(1)) {
                g.drawString(messages.getString("radioGame"), i.x + 40, i.y + 100);
                g.drawImage(radio, i.x, i.y, null);
            }
        }
    }

    public void noMoney() {
        g.setColor(new Color(255, 255, 0));
        g.drawString(messages.getString("noMoney"), width / 2 - 70, height / 2 - 70);
    }

    public void dead() {
        g.setColor(new Color(255, 255, 0));
        g.drawString(MessageFormat.format(messages.getString("petDied"), name.name), 250, 200);
        //g.drawImage(restart, 0, 0, null);
    }
    private void updateUI() {
        // Update UI elements with localized text
        mainFrame.setTitle(messages.getString("mainMenu"));
        MenuBar mb = mainFrame.getMenuBar();
        Menu menu = mb.getMenu(0);
        menu.setLabel(messages.getString("fileMenuLabel"));

        MenuItem newGame = menu.getItem(0);
        newGame.setLabel(messages.getString("newGameMenuItemLabel"));

        MenuItem continueGame = menu.getItem(1);
        continueGame.setLabel(messages.getString("continueMenuItemLabel"));

        MenuItem restart = menu.getItem(2);
        restart.setLabel(messages.getString("restartMenuItemLabel"));

        MenuItem exit = menu.getItem(3);
        exit.setLabel(messages.getString("exitMenuItemLabel"));

        Menu language = new Menu(messages.getString("languageMenuLabel"));
        engLang = new MenuItem(messages.getString("englishMenuItemLabel"));
        engLang.setActionCommand("eng");
        engLang.addActionListener(this);

        rusLang = new MenuItem(messages.getString("russianMenuItemLabel"));
        rusLang.setActionCommand("rus");
        rusLang.addActionListener(this);

    }

    public void buffer() {
        bufferStrategy.show();
        g.dispose();
    }
}


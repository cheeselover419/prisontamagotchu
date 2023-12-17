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
    BufferedImage cupcake, pizza, broccoli, bg, cutlery, home, restart, games, cursor, radio;
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
                // Handle English language action
                break;
            case "rus":
                // Handle Russian language action
                break;
            // Add more cases as needed
        }
    }

    public CanvasLayer(ArrayList<Food> foodCanvas, ArrayList<Games> gamesCanvas, Pet name) {
        this.foodCanvas = foodCanvas;
        this.gamesCanvas = gamesCanvas;
        this.name = name;



        MenuBar mb = new MenuBar();
        Menu menu = new Menu("File");
        Menu language = new Menu("Language");
        MenuItem newGame = new MenuItem("New Game", new MenuShortcut(KeyEvent.VK_N));
        newGame.setActionCommand("New");
        MenuItem continueGame = new MenuItem("Continue");
        continueGame.setActionCommand("Continue");
        MenuItem engLang = new MenuItem("English");
        engLang.setActionCommand("eng");
        MenuItem rusLang = new MenuItem("Русский");
        rusLang.setActionCommand("rus");
        language.setActionCommand("Language");
        MenuItem restart = new MenuItem("Restart", new MenuShortcut(KeyEvent.VK_R));
        restart.setActionCommand("Restart");
        MenuItem exit = new MenuItem("Exit");
        exit.setActionCommand("Exit");

        menu.add(newGame);
        menu.add(continueGame);
        menu.add(restart);
        menu.addSeparator();
        language.add(engLang);
        language.add(rusLang);
        menu.add(language);
        menu.addSeparator();
        menu.add(exit);
        mb.add(menu);

        newGame.addActionListener(this);
        continueGame.addActionListener(this);
        restart.addActionListener(this);
        exit.addActionListener(this);
        engLang.addActionListener(this);
        rusLang.addActionListener(this);

//        filepath to non animated images (food & background)
        try {
            cupcake = ImageIO.read(new File("src/images/sigarette.png"));
            pizza = ImageIO.read(new File("src/images/pizza.png"));
            broccoli = ImageIO.read(new File("src/images/broccoli.png"));
            bg = ImageIO.read(new File("src/images/bg.png"));
            cutlery = ImageIO.read(new File("src/images/cutlery.png"));
            home = ImageIO.read(new File("src/images/home.png"));
            //restart = ImageIO.read(new File("src/images/restart.png"));
            games = ImageIO.read(new File("src/images/gamepad.png"));
            cursor = ImageIO.read(new File("src/images/gamepad.png"));
            radio = ImageIO.read(new File("src/images/gamepad.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

//        setting up the frame and canvas
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(width, height);
        mainFrame.setVisible(true);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setMenuBar(mb);

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
        g.drawString("Name: " + name.name, 290, 100);

        g.setColor(new Color(0, 0, 0, 119));
        g.drawString("Health", 291, 121);
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
        g.drawString("Health", 290, 120);
        for (int i = 1; i <= name.healthLvl / 10; i++) {
            int rectPos = 350 + i * 11;
            g.drawRect(rectPos, 110, 8, 10);
        }
        g.setColor(new Color(255, 221, 0));
        g.drawString("Happiness", 285, 140);
        for (int i = 1; i <= name.happinessLvl / 10; i++) {
            int rectPos = 350 + i * 11;
            g.drawRect(rectPos, 130, 8, 10);
        }
        g.setColor(new Color(255,0,0));
        g.drawString("Money", 290, 160);
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
        g.drawString("What do you want to feed?", width / 2 - 70, height / 2 - 80);
        for (Food i : foodCanvas) {
            if (i == foodCanvas.get(0)) {
                g.drawString("Ciggarete", i.x + 30, i.y + 100);
                g.drawImage(cupcake, i.x, i.y, null);
                g.drawString("10 $", i.x + 40, i.y + 120);
            }
            if (i == foodCanvas.get(1)) {
                g.drawString("Pizza", i.x + 40, i.y + 100);
                g.drawImage(pizza, i.x, i.y, null);
                g.drawString("30 $", i.x + 40, i.y + 120);
            }
            if (i == foodCanvas.get(2)) {
                g.drawString("Broccoli", i.x + 30, i.y + 100);
                g.drawImage(broccoli, i.x, i.y, null);
                g.drawString("30 $", i.x + 40, i.y + 120);
            }
        }
    }

    public void games(){
        g.setColor(new Color(255,255,0));
        g.drawString("What do you want to play?", width / 2 - 70, height / 2 - 80);
        for (Games i : gamesCanvas){
            if (i == gamesCanvas.get(0)) {
                g.drawString("Clicker", i.x + 30, i.y + 100);
                g.drawImage(cursor, i.x, i.y, null);
            }

            if (i == gamesCanvas.get(1)){
                g.drawString("Radio", i.x + 40, i.y + 100);
                g.drawImage(radio, i.x, i.y, null);
            }
        }
    }

    public void noMoney(){
        g.setColor(new Color(255, 255, 0));
        g.drawString("You have no money!", width /2 - 70, height /2 - 70);
    }
    public void dead(){
        g.setColor(new Color(255, 255, 0));
        g.drawString("Your pet "+name.name+" died.", 250, 200);
        g.drawImage(restart, 0, 0, null);
    }



    public void buffer() {
        bufferStrategy.show();
        g.dispose();
    }
}


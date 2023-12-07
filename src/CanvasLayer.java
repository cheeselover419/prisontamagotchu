import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class CanvasLayer {
    public Pet name;
    JFrame mainFrame = new JFrame();
    Canvas canvas = new Canvas();
    ArrayList<Food> foodCanvas;
    Graphics g;
    int width = 615;
    int height = 635;
    BufferStrategy bufferStrategy;
    BufferedImage cupcake, pizza, broccoli, bg, cutlery, home, restart;

    public CanvasLayer(ArrayList<Food> foodCanvas, Pet name) {
        this.foodCanvas = foodCanvas;
        this.name = name;

//        filepath to non animated images (food & background)
        try {
            cupcake = ImageIO.read(new File("src/images/cupcake.png"));
            pizza = ImageIO.read(new File("src/images/pizza.png"));
            broccoli = ImageIO.read(new File("src/images/broccoli.png"));
            bg = ImageIO.read(new File("src/images/bg.png"));
            cutlery = ImageIO.read(new File("src/images/cutlery.png"));
            home = ImageIO.read(new File("src/images/home.png"));
            restart = ImageIO.read(new File("src/images/restart.png"));
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
        canvas.setBackground(new Color(126, 146, 203, 255));
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
        g.drawImage(cutlery, 0, 0, null);
        g.setColor(new Color(0, 0, 0));
//        g.drawRect(homeB.x, homeB.y, homeB.width, homeB.height);
//        g.drawRect(cutlB.x, cutlB.y, cutlB.width, cutlB.height);
    }
    public void petStats() {

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
            g.drawImage(ImageIO.read(new File(name.filePet)), 0, 0, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void feeding() {

        g.setColor(new Color(0, 0, 0));
        g.drawString("What do you want to feed?", width / 2 - 70, height / 2 - 80);
        for (Food i : foodCanvas) {
            if (i == foodCanvas.get(0)) {
                g.drawString("Cupcake", i.x + 30, i.y + 100);
                g.drawImage(cupcake, i.x, i.y, null);
            }
            if (i == foodCanvas.get(1)) {
                g.drawString("Pizza", i.x + 40, i.y + 100);
                g.drawImage(pizza, i.x, i.y, null);
            }
            if (i == foodCanvas.get(2)) {
                g.drawString("Broccoli", i.x + 30, i.y + 100);
                g.drawImage(broccoli, i.x, i.y, null);
            }
        }
    }

    public void illness(){

    }

    public void dead(){
        g.setColor(new Color(0, 0, 0));
        g.drawString("Your pet "+name.name+" died.", 250, 200);
        g.drawImage(restart, 0, 0, null);
    }

    public void buffer() {
        bufferStrategy.show();
        g.dispose();
    }
}


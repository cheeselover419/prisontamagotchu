package com.example;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;
import javax.swing.JOptionPane;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileOutputStream;
import java.io.FileInputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Scanner;

import javax.sound.sampled.*;

public class GameLogic {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Logger logger = LogManager.getLogger(GameLogic.class);

    public static void main(String[] args) {
        configureLogger();

        logger.info("Application started");
        Food cigarette = new Food();
        cigarette.cigarette();
        cigarette.cigaretteParameter();
        Food soup = new Food();
        soup.soup();
        soup.soupParameter();
        Food chifir = new Food();
        chifir.chifir();
        chifir.chifirParameter();


        Games clicker = new Games();
        clicker.clicker();
        clicker.clickerParameter();
        Games radio = new Games();
        radio.radio();
        radio.radioParameter();


        MenuButtons homeB = new MenuButtons();
        homeB.homeParameter();
        MenuButtons cutlB = new MenuButtons();
        cutlB.cutleryParameter();
        MenuButtons gameB = new MenuButtons();
        gameB.gameParameter();

        MenuButtons restaB = new MenuButtons();
        restaB.restartParameter();

        ArrayList<Food> foodCanvas = new ArrayList<>();
        foodCanvas.add(cigarette);
        foodCanvas.add(soup);
        foodCanvas.add(chifir);

        ArrayList<Games> gamesCanvas = new ArrayList<>();
        gamesCanvas.add(clicker);
        gamesCanvas.add(radio);

        Pet pet = new Pet();

        String filePath = "src/main/resources/Gio.wav";
        playBackgroundMusic(filePath);


        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Код, выполняемый при закрытии программы
            writeToExcelOnExit(pet.getName());
        }));
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose or create a save:",
                "Start Game",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Load Save", "New Save"},
                "Load Save"
        );

        String selectedSave;
        if (choice == JOptionPane.YES_OPTION) {
            // Load an existing save
            selectedSave = chooseSave();
            if (selectedSave != null) {
                Var.petSave = new File("saves", selectedSave + ".json");
                reloadSave(pet);
            } else {
                // Handle the case where no save is selected or created
                JOptionPane.showMessageDialog(null, "No save selected or created. Exiting.");
                return;
            }
        } else {
            // Create a new save
            selectedSave = createNewSave();
            if (selectedSave != null) {
                Var.petSave = new File("saves", selectedSave + ".json");
                // Call createNewCharacter to initialize the pet
                createNewCharacter(pet);
            } else {
                // Handle the case where no new save is created
                JOptionPane.showMessageDialog(null, "No new save created. Exiting.");
                return;
            }
        }

        if (selectedSave != null) {
            Var.petSave = new File("saves", selectedSave + ".json");

            reloadSave(pet);
        } else {
            // Handle the case where no save is selected or created
            JOptionPane.showMessageDialog(null, "No save selected or created. Exiting.");
            return;
        }
//        TODO: Bedingung anpassen
       /* if(pet.name == ""){
            pet.startValues();
        }
        else {
            reloadSave(pet);
        }

        */


        CanvasLayer c = new CanvasLayer(foodCanvas, gamesCanvas, pet);

        c.canvas.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                if (Var.switchScreen == Display.FOOD_SCREEN){
                    if (foodCollide(cigarette, e)) {
                        if(pet.lessMoney(cigarette)==true)
                        {
                            pet.moreHappiness(cigarette);
                            pet.moreHealth(cigarette);
                            logger.info("pressed cigarette");
                            Client.sendMessage(pet.name+": покурил");
                        }
                    }
                    if (foodCollide(soup, e)) {
                        if(pet.lessMoney(soup)==true)
                        {
                            pet.moreHappiness(soup);
                            pet.moreHealth(soup);
                            logger.info("pressed soup");
                            Client.sendMessage(pet.name+": поел пиццы");
                        }
                    }
                    if (foodCollide(chifir, e)) {
                        if(pet.lessMoney(chifir)==true)
                        {
                            pet.moreHappiness(chifir);
                            pet.moreHealth(chifir);
                            logger.info("pressed chifir");
                            Client.sendMessage(pet.name+": поел брокколи");
                        }
                    }
                }

                if (Var.switchScreen == Display.GAME_SCREEN){
                    if(gameCollide(clicker, e)){
                        pet.moreHappinessGame(clicker);
                        pet.moreMoney(clicker);
                        logger.info("pressed clicker");
                        Client.sendMessage(pet.name+": заработал десяточку");
                    }
                    if(gameCollide(radio, e)){
                        pet.moreHappinessGame(radio);
                        logger.info("pressed radio");

                    }

                }
                if (imageCollide(homeB, e)){
                    Var.switchScreen = Display.HOME_SCREEN;
                }
                if (imageCollide(cutlB, e)){
                    Var.switchScreen = Display.FOOD_SCREEN;;
                }
                if (imageCollide(gameB, e)){
                    Var.switchScreen = Display.GAME_SCREEN;
                }
                if (imageCollide(restaB, e) && Var.switchScreen == Display.DEAD){
                    try {
                        Var.petSave.createNewFile();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    pet.startValues();
                    c.buffer();
                }
            }
        });

        Var.healthTimer = Var.init();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Var.currentTime = System.currentTimeMillis();
                c.basicLayer();
                if (Var.switchScreen != Display.DEAD){
                    c.petStats();
                    logger.info("switch screen");

                }
                if (Var.switchScreen == Display.FOOD_SCREEN){
                    c.feeding();
                    logger.info("switch screen feeding");

                }
                if (Var.switchScreen == Display.HOME_SCREEN){
                    c.petAnimation();
                }
                if (Var.switchScreen == Display.GAME_SCREEN){
                    c.games();
                    logger.info("switch screen games");

                }
                if (Var.switchScreen == Display.DEAD){
                    c.dead();
                    logger.info("switch screen dead");

                }
                if(pet.money == 0){
                    c.noMoney();
                }
                if (pet.happinessLvl == 0 && pet.illTimer == null){
                    pet.illness();
                }
                if(pet.aPetTimer == null){
                    pet.petA();
                }
                c.buffer();
                pet.updatePet();
                if (pet.moreLove && pet.lveTimer == null) {
                    pet.petLove();
                }
                if ((Var.currentTime - Var.healthTimer) >= 500) {
                    pet.petHealth();
                    pet.petHappiness();
                    Var.healthTimer = System.currentTimeMillis();
                }
                //TODO: нужно сделать норм сохранение питомца, чтобы не было 2 сохранения(одно которое сохраняется прям при создании) и вот это которое сохраняется по хакрыют игры(нужно оставить только это)
                // и нужно сделать так, чтобы имя сохранения было такое же как и имя персонажа(т.е. если есть какие то запрещенные символы в имене(все кроме букв) они заменяются звездочками
                //нужно реализовать тут так же сохранение, чтобы оно было со звездочками
                save(pet);

            }
        }, 0, 60);
        logger.info("Application exiting");

    }

    private static String chooseSave() {
        File savesDirectory = new File("saves");
        if (savesDirectory.exists() && savesDirectory.isDirectory()) {
            File[] saveFiles = savesDirectory.listFiles();
            if (saveFiles != null && saveFiles.length > 0) {
                File selectedFile = (File) JOptionPane.showInputDialog(
                        null,
                        "Choose a save to load:",
                        "Load Save",
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        saveFiles,
                        saveFiles[0]
                );

                if (selectedFile != null) {
                    Var.petSave = selectedFile;
                    return selectedFile.getName().replaceFirst("[.][^.]+$", "");
                }
            }
        }
        logger.info("Inside chooseSave");

        return null; // No existing saves found
    }






    private static String createNewSave() {
        String saveName;
        boolean validInput = false;

        do {
            saveName = JOptionPane.showInputDialog("Enter a name for the new save:");
            if (saveName != null && !saveName.isEmpty() && saveName.matches("^[\\p{Alnum}]+$")) {
                validInput = true;
            } else {
                JOptionPane.showMessageDialog(null, "Please enter a valid name (only letters and digits).");
            }
        } while (!validInput);

        try {
            File savesDirectory = new File("saves");
            if (!savesDirectory.exists()) {
                savesDirectory.mkdir();
            }

            Var.petSave = new File(savesDirectory, saveName + ".json");
            Var.petSave.createNewFile();

            return saveName;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }





    private static void createNewCharacter(Pet pet) {

        String petSaveName = JOptionPane.showInputDialog("Enter a name for the new save:");
        if (petSaveName != null && !petSaveName.isEmpty()) {
            // Обработка текста с использованием TextProcessor
            String processedName = TextProcessor.processText(petSaveName);
            Var.petSave = new File("saves", processedName + ".json");
            pet.name = processedName;  // Обновляем имя персонажа
        } else {
            // Handle the case where the user cancels or enters an empty name
            JOptionPane.showMessageDialog(null, "Please enter a valid name. Exiting.");
            return;
        }

        pet.startValues();
        save(pet);
        logger.info("Inside createNewCharacter");

    }

    public static void save(Pet p) {
        try {
            JSONObject json = new JSONObject();
            json.put("name", p.name);
            json.put("loveLvl", p.loveLvl);
            json.put("health", p.healthLvl);
            json.put("happiness", p.happinessLvl);
            json.put("money", p.money);

            // Convert JSONObject to a formatted string
            String jsonString = json.toString(2);

            // Write the string to the file
            FileWriter fw = new FileWriter(Var.petSave);
            fw.write(jsonString);
            fw.flush();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void reloadSave(Pet p) {
        try {
            String content = Files.readString(Paths.get(Var.petSave.toURI()));

            if (!content.isEmpty()) {
                JSONObject json = new JSONObject(content);

                p.name = json.optString("name", "");
                p.loveLvl = json.optInt("loveLvl", 0);
                p.healthLvl = json.optInt("health", 0);
                p.happinessLvl = json.optInt("happiness", 0);
                p.money = json.optInt("money", 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("Inside reloadSave");

    }
    private static void writeToExcelOnExit(String petName) {
        try {
            String folderName = "excel_saves";
            File folder = new File(folderName);

            if (!folder.exists()) {
                folder.mkdir();
            }

            String fileName = petName.replaceAll("\\s", "_") + "_GameLog.xlsx";
            File excelFile = new File(folder, fileName);
            XSSFWorkbook workbook;

            if (excelFile.exists()) {
                FileInputStream fis = new FileInputStream(excelFile);
                workbook = new XSSFWorkbook(fis);
                fis.close();
            } else {
                workbook = new XSSFWorkbook();
            }

            Sheet sheet = workbook.getSheet("PetData");
            if (sheet == null) {
                sheet = workbook.createSheet("PetData");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String currentTime = dateFormat.format(new Date());

            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            row.createCell(0).setCellValue(currentTime);
            row.createCell(1).setCellValue(petName);

            try (FileOutputStream fileOut = new FileOutputStream(excelFile)) {
                workbook.write(fileOut);
            }

            workbook.close();
            logger.info("Inside writeToExcelOnExit");

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("Error in writeToExcelOnExit", e);
        }
    }


    public static void startNewGame(Pet pet, CanvasLayer canvasLayer) {
        String petName = JOptionPane.showInputDialog("Enter your pet's name:");
        if (petName != null && !petName.isEmpty()) {
            pet.name = petName;
        } else {
            // Handle the case where the user cancels or enters an empty name
            // You can add additional logic here as needed
            return;
        }

        // Add logic to reset the game state for a new game
        pet.startValues();
        canvasLayer.buffer();
        logger.info("Inside startnewgame");

    }

    public static void restartGame(Pet pet, CanvasLayer canvasLayer) {
        // Add logic to restart the game state
        try {
            Var.petSave.createNewFile();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        pet.startValues();
        canvasLayer.buffer();
        logger.info("Inside restartgame");

    }

    public static void playBackgroundMusic(String fileName) {
        try {
            File soundFile = new File(fileName);
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean foodCollide(Food food, MouseEvent mouse) {
        return mouse.getX() > food.x && mouse.getX() < (food.x + food.ovalWidth) &&
                mouse.getY() > food.y && mouse.getY() < (food.y + food.ovalHeight);
    }

    public static boolean gameCollide(Games games, MouseEvent mouse){
        return mouse.getX() > games.x && mouse.getX() < (games.x + games.ovalWidth) &&
                mouse.getY() > games.y && mouse.getY() < (games.y + games.ovalHeight);
    }
    public static boolean imageCollide(MenuButtons b,MouseEvent mouse){
        return mouse.getX() > b.x && mouse.getX() < (b.x + b.width) &&
                mouse.getY() > b.y && mouse.getY() < (b.y + b.height);

    }


    private static void configureLogger() {
        // Можете добавить свои настройки логгера здесь
        // Например, LogManager.setFactory() или установить конфигурацию программно
        // Но в данном случае, логгер будет настроен согласно файлу log4j2.xml
    }
}



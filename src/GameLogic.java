import java.awt.event.ActionEvent;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.json.JSONObject;
import javax.swing.JOptionPane;


public class GameLogic {

    public static void main(String[] args) {

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
                        }
                    }
                    if (foodCollide(soup, e)) {
                        if(pet.lessMoney(soup)==true)
                        {
                            pet.moreHappiness(soup);
                            pet.moreHealth(soup);
                        }
                    }
                    if (foodCollide(chifir, e)) {
                        if(pet.lessMoney(chifir)==true)
                        {
                            pet.moreHappiness(chifir);
                            pet.moreHealth(chifir);
                        }
                    }
                }

                if (Var.switchScreen == Display.GAME_SCREEN){
                    if(gameCollide(clicker, e)){
                        pet.moreHappinessGame(clicker);
                        pet.moreMoney(clicker);
                    }
                    if(gameCollide(radio, e)){
                        pet.moreHappinessGame(radio);
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
                }
                if (Var.switchScreen == Display.FOOD_SCREEN){
                    c.feeding();
                }
                if (Var.switchScreen == Display.HOME_SCREEN){
                    c.petAnimation();
                }
                if (Var.switchScreen == Display.GAME_SCREEN){
                    c.games();
                }
                if (Var.switchScreen == Display.DEAD){
                    c.dead();
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
                save(pet);

            }
        }, 0, 60);
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
        return null; // No existing saves found
    }






    private static String createNewSave() {
        String saveName = JOptionPane.showInputDialog("Enter a name for the new save:");
        if (saveName != null && !saveName.isEmpty()) {
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
        } else {
            JOptionPane.showMessageDialog(null, "Please enter a valid name. Exiting.");
        }
        return null;
    }




    private static void createNewCharacter(Pet pet) {
        String petName = JOptionPane.showInputDialog("Enter your pet's name:");
        if (petName != null && !petName.isEmpty()) {
            pet.name = petName;
        } else {
            // Handle the case where the user cancels or enters an empty name
            JOptionPane.showMessageDialog(null, "Please enter a valid name.");
            createNewCharacter(pet); // Retry creating a new character
            return;
        }

        // Initialize the character's attributes and save to file
        pet.startValues();
        save(pet);
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


}



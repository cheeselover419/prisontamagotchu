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

        Pet pet = new Pet("Cutie");

//        TODO: Bedingung anpassen
        if(pet.name == ""){
            pet.startValues();
        }
        else {
            reloadSave(pet);
        }

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
    public static void save(Pet p){
        try{
            String content = new String(Files.readAllBytes(Paths.get(Var.petSave.toURI())), "UTF-8");
            JSONObject json = new JSONObject(content);

            json.put("name", p.name);
            json.put("loveLvl", p.loveLvl);
            json.put("health", p.healthLvl);
            json.put("happiness", p.happinessLvl);
            json.put("money", p.money);

            FileWriter fw = new FileWriter(Var.petSave);
            fw.write(json.toString());
            fw.flush();
            fw.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void reloadSave(Pet p){
        try{
            String content = Files.readString(Paths.get(Var.petSave.toURI()));
            JSONObject json = new JSONObject(content);

            p.name = (String)json.get("name");
            p.loveLvl = json.getInt("loveLvl");
            p.healthLvl = json.getInt("health");
            p.happinessLvl = json.getInt("happiness");
            p.money = json.getInt("money");
        }
        catch (Exception e){
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
}



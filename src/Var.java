import java.io.File;

public class Var {

    public static File petSave = new File("src/save//pet.json");

    public static int animationPetFileInt = 0;

    public static long currentTime;
    public static long animationTimer;
    public static long healthTimer, loveTimer, happinessTimer, hygeneTimer, illnessTimer, deathTimer;
    public static Display switchScreen = Display.HOME_SCREEN;

    public static long init(){
        return System.currentTimeMillis();
    }
}
enum Display{
    NEW_PET,
    HOME_SCREEN,
    FOOD_SCREEN,
    ILL,
    DEAD
}

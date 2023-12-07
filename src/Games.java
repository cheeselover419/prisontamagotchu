public class Games {
    int gameValue, happinessLvl, x, y, ovalHeight, ovalWidth;

    String gameName;


    public void clicker(){
        this.gameName = "clicker";
        this.gameValue = 10;
        this.happinessLvl = 1;
    }

    public void radio(){
        this.gameName = "radio";
        this.happinessLvl = 5;
    }

    public void clickerParameter(){
        this.x = 200;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }

    public void radioParameter(){
        this.x = 300;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }
}

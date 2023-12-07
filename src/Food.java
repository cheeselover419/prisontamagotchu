public class Food {
    int foodValue, happinessLvl, x, y, ovalHeight, ovalWidth;
    String foodName;

    public void cigarette() {
        this.foodName = "cigarette";
        this.foodValue = -10;
        this.happinessLvl = 30;
    }
    public void pizza() {
        this.foodName = "Pizza";
        this.foodValue = 10;
        this.happinessLvl = 30;
    }
    public void broccoli() {
        this.foodName = "Broccoli";
        this.foodValue = 40;
        this.happinessLvl = -10;
    }

    public void cigaretteParameter() {
        this.x = 150;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }
    public void pizzaParameter() {
        this.x = 250;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }
    public void broccoliParameter() {
        this.x = 350;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }
}

package com.example;

import java.util.Timer;
import java.util.TimerTask;

public class Pet {
    int happinessLvl, healthLvl, happinessMax = 100, healthMax = 100, loveLvl = 100, money;
    String name;
    String[] petFile = {"p1", "p2", "p3"};
    String filePet = "src/images/pet/" + petFile[Var.animationPetFileInt] + ".png";
    Timer lveTimer = null, aPetTimer = null, illTimer = null, deadTimer = null;
    Boolean moreLove;

    public void petA() {
        aPetTimer = new Timer();
        Var.animationTimer = Var.init();
        aPetTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (Var.animationPetFileInt < 2) {
                    Var.animationPetFileInt++;
                } else {
                    Var.animationPetFileInt = 0;
                }
                filePet = "src/images/pet/" + petFile[Var.animationPetFileInt] + ".png";
            }
        }, 0, 600);
    }

    public Pet(){
        this.name = "Defaul Name";
    }
    public Pet(String name) {
        this.name = name;
        this.happinessMax = 100;
        this.healthMax = 100;
    }
    public String getName() {
        return name;
    }

    public void startValues() {
        this.happinessLvl = 50;
        this.healthLvl = 50;
        this.loveLvl = 0;
        this.money = 10;
    }

    public void petHealth() {
        if (healthLvl != 0) {
            healthLvl -= 1;
        }
    }

    public void petHappiness() {
        if (happinessLvl != 0) {
            happinessLvl -= 1;
        }
    }



    public void petLove() {
        lveTimer = new Timer();
        Var.loveTimer = Var.init();
        lveTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (loveLvl < 100) {
                    if (!moreLove) {
                        lveTimer.cancel();
                        lveTimer = null;
                    }
                    if ((Var.currentTime - Var.loveTimer) >= 3500) {
                        loveLvl += 5;
                        Var.loveTimer = System.currentTimeMillis();
                    }
                }
            }
        }, 0, 60);
    }

    public void updatePet() {
        moreLove = healthLvl >= 50 && happinessLvl >= 30 || healthLvl >= 30 && happinessLvl >= 50;

    }

/*        if(healthLvl>30) {
            System.out.println("Your pet" + name + "is healthy.");
        }
        else if(healthLvl>10){
            System.out.println("Please take better care of "+name+"!");
        }
        else {
            System.out.println(name+" is ill. Please give "+name+" some medicine and take better care or "+name);
        }*/

    public void moreHappiness(Food f) {
        happinessLvl = happinessLvl + f.happinessLvl;

        if (happinessLvl > 100) {
            happinessLvl = happinessMax;
        }
        if (happinessLvl < 0) {
            happinessLvl = 0;
        }
    }

    public void moreHealth(Food f) {
        healthLvl = healthLvl + f.foodValue;

        if (healthLvl > 100) {
            healthLvl = happinessMax;
        }
        if (healthLvl < 0) {
            healthLvl = 0;
        }
    }
    public boolean lessMoney(Food f){


        if(money>=f.foodCost)
        {
            money-=f.foodCost;
            return true;
        }

        return false;
    }

    public void moreHappinessGame(Games g){
        happinessLvl = happinessLvl + g.happinessLvl;

        if (happinessLvl > 100) {
            happinessLvl = happinessMax;
        }
        if (happinessLvl < 0) {
            happinessLvl = 0;
        }

    }


    public void moreMoney(Games g){
        money = money + g.gameValue;
    }

    public void illness() {
        illTimer = new Timer();
        Var.illnessTimer = Var.init();
        illTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (happinessLvl != 0) {
                    illTimer.cancel();
                    illTimer = null;
                }
                if (happinessLvl == 0) {
                    if ((Var.currentTime - Var.illnessTimer) >= 3500) {
                        Var.switchScreen = Display.ILL;
                    }
                    if ((Var.currentTime - Var.illnessTimer) >= 6500) {
                        death();
                    }
//                    Var.illnessTimer = System.currentTimeMillis();
                }
            }
        }, 0, 60);
    }
    public void death() {
        Var.switchScreen = Display.DEAD;
    }
}


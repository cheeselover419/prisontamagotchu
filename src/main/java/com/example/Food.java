package com.example;

public class Food {
    int foodValue, happinessLvl, x, y, ovalHeight, ovalWidth, foodCost;
    String foodName;

    public void cigarette() {
        this.foodName = "cigarette";
        this.foodValue = -10;
        this.happinessLvl = 30;
        this.foodCost = 10;

    }
    public void soup() {
        this.foodName = "soup";
        this.foodValue = 10;
        this.happinessLvl = 30;
        this.foodCost = 30;
    }
    public void chifir() {
        this.foodName = "chifir";
        this.foodValue = 40;
        this.happinessLvl = -10;
        this.foodCost = 30;
    }

    public void cigaretteParameter() {
        this.x = 150;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }
    public void soupParameter() {
        this.x = 250;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }
    public void chifirParameter() {
        this.x = 350;
        this.y = 250;
        this.ovalHeight = 100;
        this.ovalWidth = 70;
    }
}

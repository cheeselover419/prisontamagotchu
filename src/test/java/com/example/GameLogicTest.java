package com.example;


import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameLogicTest {

    @Test
    void saveTest() {
        // Создаем объект Pet и объект CanvasLayer
        Pet pet = new Pet();
        CanvasLayer canvasLayer = new CanvasLayer(new ArrayList<>(), new ArrayList<>(), pet);

        // Вызываем метод startNewGame, чтобы установить имя питомца
        GameLogic.startNewGame(pet, canvasLayer);

        // Вызываем метод save и проверяем, что файл сохранения создан
        GameLogic.save(pet);
        File saveFile = new File("saves", pet.name + ".json");
        assertTrue(saveFile.exists());

        // Загружаем данные из файла сохранения и проверяем, что они соответствуют ожидаемым значениям
        Pet loadedPet = new Pet();
        GameLogic.reloadSave(loadedPet);
        assertEquals(pet.name, loadedPet.name);
        assertEquals(pet.loveLvl, loadedPet.loveLvl);
        assertEquals(pet.healthLvl, loadedPet.healthLvl);
        assertEquals(pet.happinessLvl, loadedPet.happinessLvl);
        assertEquals(pet.money, loadedPet.money);
    }

    @Test
    void reloadSaveTest() {
        // Создаем объект Pet и объект CanvasLayer
        Pet pet = new Pet();
        CanvasLayer canvasLayer = new CanvasLayer(new ArrayList<>(), new ArrayList<>(), pet);

        // Вызываем метод startNewGame, чтобы установить имя питомца
        GameLogic.startNewGame(pet, canvasLayer);

        // Вызываем метод save, чтобы сохранить данные питомца
        GameLogic.save(pet);

        // Загружаем данные из файла сохранения и проверяем, что они соответствуют ожидаемым значениям
        Pet loadedPet = new Pet();
        GameLogic.reloadSave(loadedPet);
        assertEquals(pet.name, loadedPet.name);
        assertEquals(pet.loveLvl, loadedPet.loveLvl);
        assertEquals(pet.healthLvl, loadedPet.healthLvl);
        assertEquals(pet.happinessLvl, loadedPet.happinessLvl);
        assertEquals(pet.money, loadedPet.money);
    }

    @Test
    void startNewGameTest() {
        // Создаем объект Pet и объект CanvasLayer
        Pet pet = new Pet();
        CanvasLayer canvasLayer = new CanvasLayer(new ArrayList<>(), new ArrayList<>(), pet);

        // Вызываем метод startNewGame
        GameLogic.startNewGame(pet, canvasLayer);

        // Проверяем, что имя питомца установлено и не пустое
        assertNotNull(pet.name);
        assertFalse(pet.name.isEmpty());
    }

    @Test
    void restartGameTest() {
        // Создаем объект Pet и объект CanvasLayer
        Pet pet = new Pet();
        CanvasLayer canvasLayer = new CanvasLayer(new ArrayList<>(), new ArrayList<>(), pet);

        // Вызываем метод restartGame
        GameLogic.restartGame(pet, canvasLayer);

        // Проверяем, что файл сохранения создан заново
        File saveFile = new File("saves", pet.name + ".json");
        assertTrue(saveFile.exists());

        // Загружаем данные из файла сохранения и проверяем, что они соответствуют ожидаемым значениям после перезапуска
        Pet loadedPet = new Pet();
        GameLogic.reloadSave(loadedPet);
        assertEquals(pet.name, loadedPet.name);
        assertEquals(pet.loveLvl, loadedPet.loveLvl);
        assertEquals(pet.healthLvl, loadedPet.healthLvl);
        assertEquals(pet.happinessLvl, loadedPet.happinessLvl);
        assertEquals(pet.money, loadedPet.money);
    }
}

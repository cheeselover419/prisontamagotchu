package com.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessor {

    public static String processText(String input) {
        // Заменяем все символы, кроме букв, на звездочки
        String cleanedText = input.replaceAll("[^a-zA-Z]", "*");

        // Ваша логика обработки текста

        return cleanedText;
    }

}

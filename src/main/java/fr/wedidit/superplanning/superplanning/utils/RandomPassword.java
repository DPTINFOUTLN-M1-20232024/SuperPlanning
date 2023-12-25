package fr.wedidit.superplanning.superplanning.utils;

import java.util.Random;

public class RandomPassword {

    private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!?_.éèà-";
    private static final int SIZE = 16;
    private static final Random RANDOM = new Random();

    public static String generate() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < SIZE; i++) {
            builder.append(ALLOWED_CHARACTERS.charAt(RANDOM.nextInt(ALLOWED_CHARACTERS.length())));
        }

        return builder.toString();
    }

    private RandomPassword() {}

}

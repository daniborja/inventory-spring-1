package com.alex.inventorymanagement.common.helpers;

import java.util.Random;


public class SkuGenerator {
    private static final Random random = new Random();
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generateSku(String categoryName) {
        StringBuilder skuBuilder = new StringBuilder();

        char[] cleanedPrefix = categoryName.replaceAll("\\P{L}", "").toUpperCase().toCharArray();
        for (char ch : cleanedPrefix) {
            skuBuilder.append(ch);
        }

        int randomNumber = random.nextInt(100000);
        String randomNumberStr = String.format("%05d", randomNumber);

        char randomChar = ALPHABET.charAt(random.nextInt(ALPHABET.length()));

        return skuBuilder.append(randomNumberStr).append(randomChar).toString();
    }

}

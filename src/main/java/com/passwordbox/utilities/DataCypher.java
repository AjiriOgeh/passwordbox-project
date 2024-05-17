package com.passwordbox.utilities;

import java.security.SecureRandom;

public class DataCypher {

    private static SecureRandom secureRandom = new SecureRandom();
    private static String characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ~`!@#$%^&*()-=_+[{]};:',<.>/?";

    public static String encryptData(String data) {
        StringBuilder encryptedData = new StringBuilder();
        int secretKey = secureRandom.nextInt(28, 101);

        for (int count = 0; count < data.length(); count++){
            char element = data.charAt(count);
            element = (char)((element + secretKey + 61) % 93 + 32);
            encryptedData.append(element);
        }
        secretKey += 5;
        encryptedData.append((char)secretKey);

        int numberOfExtraCharacters = secureRandom.nextInt(8, 15);

        for (int count = 0; count < numberOfExtraCharacters; count++){
            int randomIndex = secureRandom.nextInt(characters.length());
            encryptedData.append(characters.charAt(randomIndex));
        }
        encryptedData.append((char)numberOfExtraCharacters);
        return encryptedData.toString();
    }

    public static String decryptData(String encryptedData) {
        StringBuilder decryptedData = new StringBuilder();

        int numberOfCharacters = encryptedData.charAt(encryptedData.length() -1);
        int secretKeyIndex = encryptedData.length() - (2 + numberOfCharacters);
        int secretKey = encryptedData.charAt(secretKeyIndex) - 5;

        for (int count = 0; count < secretKeyIndex; count++){
            char element = encryptedData.charAt(count);
            element = (char)((element - secretKey + 61) % 93 + 32);
            decryptedData.append(element);
        }
        return decryptedData.toString();
    }

    public static void main(String[] args) {
        String encryptedData1 = encryptData("creditcard");
        String encryptedData2 = encryptData("creditcard");
        System.out.println(encryptedData1);
        System.out.println(encryptedData2);
        System.out.println(decryptData(encryptedData1));
        System.out.println(decryptData(encryptedData2));
    }

}

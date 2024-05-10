package com.passwordbox.utilities;

import com.passwordbox.dataTransferObjects.requests.GeneratePasswordRequest;

import java.security.SecureRandom;
import java.time.LocalDate;

public final class PasscodeGenerator {
    private static SecureRandom secureRandom = new SecureRandom();
    private static String numericCharacters = "0123456789";
    private static String upperCaseCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static String lowerCaseCharacters = "abcdefghijklmnopqrstuvwxyz";
    private static String specialCharacters = "~`!@#$%^&*()-=_+[{]};:',<.>/? ";


    public static String generatePassword(GeneratePasswordRequest generatePasswordRequest){
        StringBuilder password = new StringBuilder();
        String characters = "";

        if (generatePasswordRequest.isNumericCharacters()) characters += numericCharacters;
        if (generatePasswordRequest.isUpperCaseCharacters()) characters += upperCaseCharacters;
        if (generatePasswordRequest.isLowerCaseCharacters()) characters += lowerCaseCharacters;
        if (generatePasswordRequest.isSpecialCharacters()) characters += specialCharacters;
        for (int count = 0; count < Integer.parseInt(generatePasswordRequest.getLength()); count++){
            int randomIndex = secureRandom.nextInt(characters.length());
            password.append(characters.charAt(randomIndex));
        }
        System.out.println(characters);
        return password.toString();
    }

    public static void main(String[] args) {
//        GeneratePasswordRequest generatePasswordRequest = new GeneratePasswordRequest();
//        generatePasswordRequest.setLength("9");
//        generatePasswordRequest.setLowerCaseCharacters(true);
//        generatePasswordRequest.setNumericCharacters(true);
//        generatePasswordRequest.setSpecialCharacters(false);
//        generatePasswordRequest.setUpperCaseCharacters(false);
//        System.out.println(generatePassword(generatePasswordRequest));
//        System.out.println((int)'0');

        int currentMonth = LocalDate.now().getMonthValue();
        System.out.println(currentMonth);
    }

}

package com.passwordbox.utilities;

import com.passwordbox.dataTransferObjects.requests.GeneratePasswordRequest;
import com.passwordbox.exceptions.GeneratePasswordException;

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

        if (generatePasswordRequest.getNumericCharactersChoice().equalsIgnoreCase("YES")) characters += numericCharacters;
        if (generatePasswordRequest.getUppercaseCharactersChoice().equalsIgnoreCase("YES")) characters += upperCaseCharacters;
        if (generatePasswordRequest.getLowercaseCharactersChoice().equalsIgnoreCase("YES")) characters += lowerCaseCharacters;
        if (generatePasswordRequest.getSpecialCharactersChoice().equalsIgnoreCase("YES")) characters += specialCharacters;

        if(characters.isEmpty()) throw new GeneratePasswordException("Please enter 'Yes' in a character choice to generate password.");
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

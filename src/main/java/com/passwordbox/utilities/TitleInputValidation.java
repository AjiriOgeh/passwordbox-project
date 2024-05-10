package com.passwordbox.utilities;

import com.passwordbox.data.models.Vault;

public class TitleInputValidation {

    public static  void validateLoginInfoTitle(String title, Vault vault) {
        validateTitleInput(title);
        if (doesLoginInfoTitleExist(title, vault)) throw new IllegalArgumentException("Title already exists. Please enter a different title.");
    }

    private static boolean doesLoginInfoTitleExist(String title, Vault vault) {
        for(int count = 0; count < vault.getLoginInfos().size(); count++){
            if (vault.getLoginInfos().get(count).getTitle().equalsIgnoreCase(title.toLowerCase()))
                return true;
        }
        return false;
    }

    public static void validateEditedLoginInfoTitle(String title, String editedTitle, Vault vault) {
        validateEditedTitleInput(editedTitle);
        if(doesEditedLoginInfoTitleExist(title, editedTitle, vault)) throw new IllegalArgumentException("Title already exists. Please enter a different title.");
    }

    private static boolean doesEditedLoginInfoTitleExist(String title, String editedTitle, Vault vault) {
        if (title.equalsIgnoreCase(editedTitle)) return false;
        for(int count = 0; count < vault.getLoginInfos().size(); count++){
            if (vault.getPassports().get(count).getTitle().equalsIgnoreCase(editedTitle))
                return true;
        }
        return false;
    }

    public static void validateCreditCardTitle(String title, Vault vault) {
        validateTitleInput(title);
        if (doesCreditCardTitleExist(title, vault)) throw new IllegalArgumentException("Title already exists. Please enter a different title.");
    }

    private static boolean doesCreditCardTitleExist(String title, Vault vault) {
        for(int count = 0; count < vault.getCreditCards().size(); count++){
            if (vault.getCreditCards().get(count).getTitle().equalsIgnoreCase(title.toLowerCase()))
                return true;
        }
        return false;
    }

    public static void validateEditedCreditCardTitle(String title, String editedTitle, Vault vault) {
        validateEditedTitleInput(editedTitle);
        if(doesEditedCreditCardTitleExist(title, editedTitle, vault)) throw new IllegalArgumentException("Title already exists. Please enter a different title.");
    }

    private static boolean doesEditedCreditCardTitleExist(String title, String editedTitle, Vault vault) {
        if (title.equalsIgnoreCase(editedTitle)) return false;
        for(int count = 0; count < vault.getCreditCards().size(); count++){
            if (vault.getPassports().get(count).getTitle().equalsIgnoreCase(editedTitle))
                return true;
        }
        return false;
    }

    public static void validatePassportTitle(String title, Vault vault) {
        validateTitleInput(title);
        if(doesPassportTitleExist(title, vault)) throw new IllegalArgumentException("Title already exists. Please enter a different title.");
    }

    public static boolean doesPassportTitleExist(String title, Vault vault){
        for(int count = 0; count < vault.getPassports().size(); count++){
            if (vault.getPassports().get(count).getTitle().equalsIgnoreCase(title.toLowerCase()))
                return true;
        }
        return false;
    }

    public static void validateEditedPassportTitle(String title, String editedTitle, Vault vault) {
        validateEditedTitleInput(editedTitle);
        if(doesEditedPassportTitleExist(title, editedTitle, vault)) throw new IllegalArgumentException("Title already exists. Please enter a different title.");
    }

    private static boolean doesEditedPassportTitleExist(String title, String editedTitle, Vault vault) {
        if (title.equalsIgnoreCase(editedTitle)) return false;
        for(int count = 0; count < vault.getPassports().size(); count++){
            if (vault.getPassports().get(count).getTitle().equalsIgnoreCase(editedTitle))
                return true;
        }
        return false;
    }

    private static void validateTitleInput(String title) {
        if (title == null) throw new IllegalArgumentException("Title field cannot be null. Please enter a valid title.");
        if (title.isEmpty()) throw new IllegalArgumentException("Title field cannot be empty. Please enter a valid title.");
        if (title.length() > 40) throw new IllegalArgumentException("Title cannot be more than 40 Characters. Please enter a valid title.");
    }

    private static void validateEditedTitleInput(String editedTitle) {
        if (editedTitle != null && editedTitle.isEmpty()) throw new IllegalArgumentException("Title field cannot be empty. Please enter a valid title.");
        if (editedTitle != null && editedTitle.length() > 40) throw new IllegalArgumentException("Title cannot be more than 40 Characters. Please enter a valid title.");
    }
}

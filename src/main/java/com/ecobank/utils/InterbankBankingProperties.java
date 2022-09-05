package com.ecobank.utils;

import java.util.ResourceBundle;

public class InterbankBankingProperties {
    private static ResourceBundle resource;

    public static String getMessage(String key) {
        if (resource == null)
            resource = ResourceBundle.getBundle("application");
        return resource.getString(key);
    }

    public static void main(String[] args) {
        try {
            System.out.println("This " + getMessage("ENG"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
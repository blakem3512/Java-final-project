/*
    Created by:    Hilary Philistin,
                   Matthew Blake, 
                   Henry Sesay, 
                   Braden Henry, 
                   Zach Thompson, 
                   Wyatt Metcalf
    Created on:    05/08/2025
    Team’s name:   Pitcher
    Description:   Create a baseball statistics program that produce a report's 
                   list of the statistics for all pitchers in that game and 
                   calculates the earned run average for each pitcher.
*/

package com.mycompany.pitcherteamapp;

import java.util.Scanner;

public class Console {
    
    private static final Scanner sc = new Scanner(System.in);

    public static String getString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine();
            if (s.isEmpty()) {
                System.out.println("Error! Entry is required.");
            } else {
                return s;
            }
        }  
    }

    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Error! Invalid integer value.");
            }
        }
    }

    public static int getInt(String prompt, int min, int max) {
        while (true) {
            int value = getInt(prompt);
            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.println("Error! Number must be greater than " 
                        + min + " and less than " + max + ".");
            } 
        }
    }

    public static double getDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(sc.nextLine());
            } catch(NumberFormatException e) {
                System.out.println("Error! Invalid integer value.");
            }
        }
    }

    public static double getDouble(String prompt, double min, double max) {
        while (true) {
            double value = getDouble(prompt);
            if (value >= min && value <= max) {
                return value;
            } else {
                System.out.println("Error! Number must be greater than " 
                        + min + " and less than " + max + ".");
            } 
        }
    }
}

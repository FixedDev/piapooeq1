package me.equipo1.pos.views;

import java.util.Scanner;

public interface View {
    /**
     * Shows this view
     *
     * @return The not null view to change to, if null, the program will terminate.
     */
    View show();

    static void pause(Scanner scanner) {
        println("Presione enter para continuar...");
        scanner.nextLine();
        scanner.nextLine();
    }

    static void println(String str) {
        System.out.println(str);
    }

    static void println() {
        System.out.println();
    }

    static void print(String str) {
        System.out.print(str);
    }

    static void printCentered(String str, int width) {
        int spaces = (width / 2) - str.length() + 1;
        if (spaces < 0) {
            spaces = 0;
        }

        print(" ".repeat(spaces));
        print(str.substring(0, Math.min(str.length(), width)));
    }

    static void printLeft(String str, int width) {
        int spaces = width - str.length();
        if (spaces < 0) {
            spaces = 0;
        }

        print(str.substring(0, Math.min(str.length(), width)));
        print(" ".repeat(spaces));
    }

    static void printRight(String str, int width) {
        int spaces = width - str.length();
        if (spaces < 0) {
            spaces = 0;
        }

        print(" ".repeat(spaces));
        print(str.substring(0, Math.min(str.length(), width)));
    }

    static void clear() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }
}

package me.equipo1.pos.views;

import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class MainView implements View {

    private final Scanner scanner;
    private final ViewManager viewManager;

    public MainView(Scanner scanner, ViewManager viewManager) {
        this.scanner = scanner;
        this.viewManager = viewManager;
    }

    @Override
    public View show() {
        clear();
        println("""
                ---------------------
                | Abarrotes Bisonte |
                ---------------------
                """);
        println("Opciones: ");
        println("""
                1. Inventario
                2. Ventas
                3. Salir
                """);

        println("Ingrese opcion: ");
        int opcion;

        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }

        opcion = scanner.nextInt();

        if (opcion > 4 || opcion < 1) {
            return this;
        }

        return switch (opcion) {
            case 1 -> viewManager.getView("inventory").orElse(this);
            case 2 -> viewManager.getView("sales").orElse(this);
            case 3 -> null;
            default -> this;
        };

    }
}

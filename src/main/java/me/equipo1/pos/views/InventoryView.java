package me.equipo1.pos.views;

import me.equipo1.pos.datastore.Datastore;
import me.equipo1.pos.inventory.Inventory;

import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class InventoryView implements View {

    private final ViewManager viewManager;
    private final Scanner scanner;
    public InventoryView(ViewManager viewManager, Scanner scanner) {
        this.viewManager = viewManager;
        this.scanner = scanner;
    }

    @Override
    public View show() {
        clear();
        println("""
                ---------------------
                | Abarrotes Bisonte |
                |     Inventario    |
                ---------------------
                """);
        println("Opciones: ");
        println("""
                1. Bajas
                2. Altas
                3. Actualizar
                4. Devoluciones
                5. Salir
                """);

        println("Ingrese opcion: ");
        int opcion;

        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }

        opcion = scanner.nextInt();

        if (opcion > 5 || opcion < 1) {
            return this;
        }

        return switch (opcion) {
            case 1 -> viewManager.getView("delete").orElse(this);
            case 2 -> viewManager.getView("register").orElse(this);
            case 3 -> viewManager.getView("update").orElse(this);
            case 4 -> viewManager.getView("returns").orElse(this);
            case 5 -> viewManager.getView("main").orElse(null);
            default -> this;
        };

    }
}

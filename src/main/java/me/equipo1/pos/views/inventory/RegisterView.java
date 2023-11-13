package me.equipo1.pos.views.inventory;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.items.ItemData;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;

import java.util.Optional;
import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class RegisterView implements View {

    private final Scanner scanner;
    private final ViewManager viewManager;
    private final Inventory inventory;

    public RegisterView(Scanner scanner, ViewManager viewManager, Inventory inventory) {
        this.scanner = scanner;
        this.viewManager = viewManager;
        this.inventory = inventory;
    }


    @Override
    public View show() {
        clear();
        println("""
                ---------------------
                | Abarrotes Bisonte |
                |  Alta Inventario  |
                ---------------------
                """);
        print("Articulo: ");
        String id = scanner.next();

        Optional<ItemData> optItem = inventory.itemById(id);

        if (optItem.isEmpty()) {
            println("No se pudo encontrar el articulo " + id);
            println("Desea crearlo en el inventario? S/N: ");

            char opcion = Character.toLowerCase(scanner.next().charAt(0));

            if (opcion == 's') {
                return viewManager.getView("create-item").orElse(null);
            } else {
                return this;
            }
        }

        print("Cantidad: ");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }

        int quantity = Math.abs(scanner.nextInt());
        inventory.addItem(optItem.get(), quantity);

        println("Agregados " + quantity + " de " + optItem.get().name());
        pause(scanner);

        return viewManager.getView("inventory").orElse(null);
    }
}

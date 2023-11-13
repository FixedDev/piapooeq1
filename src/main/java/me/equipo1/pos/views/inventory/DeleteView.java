package me.equipo1.pos.views.inventory;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.items.ItemData;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;

import java.util.Optional;
import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class DeleteView implements View {

    private final Scanner scanner;
    private final ViewManager viewManager;
    private final Inventory inventory;

    public DeleteView(Scanner scanner, ViewManager viewManager, Inventory inventory) {
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
                |  Baja Inventario  |
                ---------------------
                """);
        print("Articulo: ");
        String id = scanner.next();

        Optional<ItemData> optItem = inventory.itemById(id);

        if (optItem.isEmpty()) {
            println("No se pudo encontrar el articulo " + id);
            pause(scanner);

            return viewManager.getView("inventory").orElse(null);
        }

        char opcion;
        do {
            print("Eliminar los datos del articulo S/N: ");
            opcion = Character.toLowerCase(scanner.next().charAt(0));
        } while (opcion != 's' && opcion != 'n');

        if (opcion == 's') {
            inventory.deleteItem(optItem.get());
            println("Articulo eliminado correctamente de la base de datos.");
            pause(scanner);

            return viewManager.getView("inventory").orElse(null);
        }

        print("Cantidad: ");
        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }

        int quantity = Math.abs(scanner.nextInt());
        if (!inventory.has(optItem.get(), quantity)) {
            println("No hay suficiente cantidad de " + optItem.get().name() + "(" + optItem.get().name() + ").");
            pause(scanner);

            return viewManager.getView("inventory").orElse(null);
        }

        inventory.addItem(optItem.get(), -quantity);

        println("Eliminados " + quantity + " de " + optItem.get().name());
        pause(scanner);

        return viewManager.getView("inventory").orElse(null);
    }
}

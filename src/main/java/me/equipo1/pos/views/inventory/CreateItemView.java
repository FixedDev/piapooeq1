package me.equipo1.pos.views.inventory;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.items.ItemData;
import me.equipo1.pos.items.ItemRegistry;
import me.equipo1.pos.items.PojoItemData;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;

import java.util.Optional;
import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class CreateItemView implements View {

    private final Scanner scanner;
    private final ViewManager viewManager;
    private final Inventory inventory;

    public CreateItemView(Scanner scanner, ViewManager viewManager, Inventory inventory) {
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

        if (optItem.isPresent()) {
            println("El articulo " + id + " ya existe en el inventario.");
            pause(scanner);

            return viewManager.getView("inventory").orElse(null);
        }

        print("Nombre: ");
        String name = scanner.next();

        print("Precio: ");
        double price;
        do {
            while (!scanner.hasNextDouble()) {
                scanner.nextLine();
            }
            price = scanner.nextDouble();
        } while (price < 0);

        print("Stock minimo: ");
        int minStock;
        do {
            while (!scanner.hasNextInt()) {
                scanner.nextLine();
            }
            minStock = scanner.nextInt();
        } while (minStock < 0);

        ItemData data = new PojoItemData(id, name, price, minStock);

        println("\n");
        printItem(data);
        println("\n");

        char opcion;
        do {
            print("Es correcta la informacion? S/N: ");
            opcion = Character.toLowerCase(scanner.next().charAt(0));
        } while (opcion != 's' && opcion != 'n');

        if (opcion == 's') {
            inventory.registerItem(data);
            println("Articulo guardado correctamente en la base de datos.");
            pause(scanner);

            return viewManager.getView("inventory").orElse(null);
        }

        return this;
    }

    private void printItem(ItemData itemData) {
        printLeft("Codigo", 20);
        printLeft("Nombre", 20);
        printLeft("Precio", 20);
        printLeft("Stock minimo", 20);
        println();

        printLeft(itemData.id(), 20);
        printLeft(itemData.name(), 20);
        printLeft(String.valueOf(itemData.price()), 20);
        printLeft(String.valueOf(itemData.minQuantity()), 20);
        println();
    }
}

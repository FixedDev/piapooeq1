package me.equipo1.pos.views.inventory;

import me.equipo1.pos.items.ItemData;
import me.equipo1.pos.items.ItemRegistry;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;

import java.util.Optional;
import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class UpdateItemView implements View {

    private final Scanner scanner;
    private final ViewManager viewManager;
    private final ItemRegistry itemRegistry;

    public UpdateItemView(Scanner scanner, ViewManager viewManager, ItemRegistry itemRegistry) {
        this.scanner = scanner;
        this.viewManager = viewManager;
        this.itemRegistry = itemRegistry;
    }

    @Override
    public View show() {
        clear();
        println("""
                ------------------------------
                |     Abarrotes Bisonte      |
                |  Actualizacion Inventario  |
                ------------------------------
                """);
        print("Articulo: ");
        String id = scanner.next();

        Optional<ItemData> optItem = itemRegistry.getIfCached(id);

        if (optItem.isEmpty()) {
            println("El articulo " + id + " no existe en el inventario.");
            pause(scanner);

            return viewManager.getView("inventory").orElse(null);
        }

        ItemData data = optItem.get();

        println("Opciones: ");
        println("""
                1. Nombre
                2. Precio
                3. Stock minimo
                4. Salir
                """);
        println("Ingrese opcion: ");
        int opcion;

        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }

        opcion = scanner.nextInt();

        switch (opcion) {
            case 1:
                print("Nombre: ");
                data.setName(scanner.next());
                break;
            case 2:
                print("Precio: ");
                double price;

                do {
                    while (!scanner.hasNextDouble()) {
                        scanner.nextLine();
                    }
                    price = scanner.nextDouble();
                } while (price < 0);

                data.setPrice(price);
                break;
            case 3:
                print("Stock minimo: ");
                int minStock;
                do {
                    while (!scanner.hasNextInt()) {
                        scanner.nextLine();
                    }
                    minStock = scanner.nextInt();
                } while (minStock < 0);

                data.setMinStock(minStock);
                break;
        }

        println("\n");
        printItem(data);
        println("\n");

        do {
            print("Es correcta la informacion? S/N: ");
            opcion = Character.toLowerCase(scanner.next().charAt(0));
        } while (opcion != 's' && opcion != 'n');

        if (opcion == 's') {
            itemRegistry.updateItem(data);
            println("Articulo actualizado correctamente en la base de datos.");
            pause(scanner);

            return viewManager.getView("inventory").orElse(null);
        }

        return this;
    }

    private void printItem(ItemData itemData) {
        printLeft("Codigo", 20);
        printLeft("Nombre", 30);
        printLeft("Precio", 20);
        printLeft("Stock minimo", 20);
        println();

        printLeft(itemData.id(), 20);
        printLeft(itemData.name(), 30);
        printLeft(String.valueOf(itemData.price()), 20);
        printLeft(String.valueOf(itemData.minQuantity()), 20);
        println();
    }
}

package me.equipo1.pos.views.inventory;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.items.ItemData;
import me.equipo1.pos.items.ItemRegistry;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;

import java.util.Optional;
import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class ShowInventoryView implements View {
    private final Scanner scanner;
    private final ViewManager viewManager;
    private final Inventory inventory;
    private final ItemRegistry itemRegistry;

    public ShowInventoryView(Scanner scanner, ViewManager viewManager, Inventory inventory, ItemRegistry itemRegistry) {
        this.scanner = scanner;
        this.viewManager = viewManager;
        this.inventory = inventory;
        this.itemRegistry = itemRegistry;
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

        println("Articulo a mostrar (* o todos para todos los articulos): ");
        String input = scanner.next();
        printColumns();

        if (input.equalsIgnoreCase("todos") || input.equalsIgnoreCase("*")) {
            itemRegistry.allItems().thenAccept(itemData -> {
                for (ItemData itemDatum : itemData) {
                    printItem(itemDatum);
                }

                println("-".repeat(110));
            });

            pause(scanner);
        } else {
            Optional<ItemData> optItem = inventory.itemById(input);

            if (optItem.isEmpty()) {
                println("No se pudo encontrar el articulo " + input);
                pause(scanner);

                return viewManager.getView("inventory").orElse(null);
            }

            ItemData itemData = optItem.get();
            printItem(itemData);
            println("-".repeat(110));

            pause(scanner);
        }

        return viewManager.getView("inventory").orElse(null);
    }

    private void printColumns() {
        println("-".repeat(120));
        printCentered("Articulos", 120);
        println();
        println("-".repeat(120));

        printLeft("Codigo", 20);
        printLeft("Nombre", 30);
        printLeft("Precio", 20);
        printLeft("Stock minimo", 20);
        printLeft("Stock", 10);
        println();

        println("-".repeat(120));
    }

    private void printItem(ItemData itemData) {
        printLeft(itemData.id(), 20);
        printLeft(itemData.name(), 30);
        printLeft(String.valueOf(itemData.price()), 20);
        printLeft(String.valueOf(itemData.minQuantity()), 20);
        printLeft(String.valueOf(inventory.stock(itemData)), 10);
        println();
    }
}

package me.equipo1.pos.views;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.sales.Cart;
import me.equipo1.pos.sales.CartManager;
import me.equipo1.pos.sales.PojoCart;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;
import me.equipo1.pos.views.sales.CartView;

import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class SalesView implements View {

    private final ViewManager viewManager;
    private final Scanner scanner;

    private CartManager cartManager;

    public SalesView(ViewManager viewManager, Scanner scanner, CartManager cartManager) {
        this.viewManager = viewManager;
        this.scanner = scanner;
        this.cartManager = cartManager;
    }

    @Override
    public View show() {
        clear();
        println("""
                ---------------------
                | Abarrotes Bisonte |
                |       Ventas      |
                ---------------------
                """);
        println("Ingrese numero de carrito o 6 para salir: ");
        int opcion;

        while (!scanner.hasNextInt()) {
            scanner.nextLine();
        }

        opcion = scanner.nextInt();

        if (opcion > 6 || opcion < 1) {
            return this;
        }

        if (opcion == 6) {
            return viewManager.getView("main").orElse(this);
        }

        Cart cart = cartManager.getCart(opcion - 1).get(); // we know that it exists.

        return new CartView(scanner, cart, viewManager);
    }
}

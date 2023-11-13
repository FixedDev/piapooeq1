package me.equipo1.pos.views.sales;

import me.equipo1.pos.sales.Cart;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;

import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class CartView implements View {

    private final Scanner scanner;
    private final Cart cart;
    private final ViewManager viewManager;
    private final CartOptionView optionView;

    private View returnTo = null;

    public CartView(Scanner scanner, Cart cart, ViewManager viewManager) {
        this.scanner = scanner;
        this.cart = cart;
        this.viewManager = viewManager;

        optionView = new CartOptionView(scanner, cart, viewManager, this);
        returnTo = optionView;
    }


    @Override
    public View show() {
        clear();
        println("""
                ---------------------
                | Abarrotes Bisonte |
                |      Carrito      |
                ---------------------
                """);

        println("-".repeat(110));
        printCentered("Articulos", 110);
        println();
        println("-".repeat(110));

        printLeft("Codigo", 20);
        printLeft("Nombre", 30);
        printLeft("Cantidad", 20);
        printRight("Costo Unitario", 20);
        printRight("Costo", 20);

        println();
        println("-".repeat(110));

        cart.itemsInCart().forEach((itemData, integer) -> {
            if (integer == 0) {
                return;
            }

            printLeft(itemData.id(), 20);
            printLeft(itemData.name(), 30);
            printLeft(String.valueOf(integer), 20);
            printRight(String.valueOf(itemData.price()), 20);
            printRight(String.valueOf(itemData.price() * integer), 20);
            println();
        });

        println("-".repeat(110));

        printRight("Costo: " + cart.getPrice(), 110);
        println();

        if (returnTo == optionView) {
            optionView.setReturnTo(this);
        }

        return returnTo;
    }

    public void setReturnTo(View view) {
        this.returnTo = view;
    }
}

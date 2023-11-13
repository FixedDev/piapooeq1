package me.equipo1.pos.views.sales;

import me.equipo1.pos.sales.Cart;
import me.equipo1.pos.views.View;
import me.equipo1.pos.views.ViewManager;

import java.util.Scanner;

import static me.equipo1.pos.views.View.println;

import static me.equipo1.pos.views.View.*;

public class CartOptionView implements View {
    private final Scanner scanner;
    private final Cart cart;
    private final ViewManager viewManager;

    private View returnTo = null;
    private final CartView parent;

    public CartOptionView(Scanner scanner, Cart cart, ViewManager viewManager, CartView parent) {
        this.scanner = scanner;
        this.cart = cart;
        this.viewManager = viewManager;
        this.parent = parent;

        returnTo = parent;
    }

    @Override
    public View show() {
        println("Opciones especiales: pagar, devolver, reset, salir");
        print("Ingrese: ");
        String input = scanner.next();

        if (input.equals("salir")) {
            return viewManager.getView("sales").orElse(null);
        }

        if (input.equalsIgnoreCase("pagar")) {
            return new PayCartView(scanner, cart, this, parent);
        }

        if (input.equalsIgnoreCase("reset")) {
            cart.reset();

            return parent;
        }

        if (input.equalsIgnoreCase("devolver")) {
            cart.returnItems();
            cart.reset();

            println("Se devolvieron los articulos del carrito al inventario.");
            pause(scanner);

            return parent;
        }

        try {
            cart.itemOperation(input);
        } catch (IllegalArgumentException ignored) {
            println("No se pudo encontrar el item " + input);
            pause(scanner);
        }

        if (returnTo == parent) {
            parent.setReturnTo(this);
        }

        return returnTo;
    }

    public void setReturnTo(View view) {
        this.returnTo = view;
    }
}

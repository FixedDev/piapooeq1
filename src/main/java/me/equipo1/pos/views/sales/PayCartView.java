package me.equipo1.pos.views.sales;

import me.equipo1.pos.sales.Cart;
import me.equipo1.pos.views.View;

import java.util.Scanner;

import static me.equipo1.pos.views.View.*;

public class PayCartView implements View {

    private final Scanner scanner;
    private final Cart cart;
    private final View parent;
    private final CartView cartView;

    public PayCartView(Scanner scanner, Cart cart, View parent, CartView cartView) {
        this.scanner = scanner;
        this.cart = cart;
        this.parent = parent;
        this.cartView = cartView;
    }

    @Override
    public View show() {
        cartView.setReturnTo(this);
        cartView.show();
        cartView.setReturnTo(parent);

        if (cart.isAlreadyPaid()) {
            println("Carrito pagado totalmente.");
            println("Feria/Vuelto: " + cart.moneyChange());
            pause(scanner);

            cart.sell();
            cart.reset();

            return cartView;
        }

        println("Restante de pagar: " + (cart.getPrice() - cart.paid()));

        print("Ingrese: ");
        String input = scanner.next();

        if (input.equalsIgnoreCase("salir")) {
            return cartView;
        }

        double toPay = Double.parseDouble(input);
        cart.pay(toPay);

        return this;
    }
}

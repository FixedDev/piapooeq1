package me.equipo1.pos.sales;

import me.equipo1.pos.inventory.Inventory;

import java.util.Optional;

public class CartManager {
    private final Cart[] carts;
    private final Inventory inventory;

    public CartManager(Inventory inventory) {
        this.inventory = inventory;
        carts = new Cart[5];
    }

    public Optional<Cart> getCart(int cart) {
        if(cart >= carts.length) {
            return Optional.empty();
        }

        if(carts[cart] == null) {
            carts[cart] = new PojoCart(inventory);
        }

        return Optional.of(carts[cart]);
    }
}

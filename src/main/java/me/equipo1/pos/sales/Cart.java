package me.equipo1.pos.sales;

import me.equipo1.pos.items.ItemData;

import java.util.Map;

public interface Cart {
    void addItem(String id);

    void removeItem(String id);

    void itemOperation(String input);

    double getPrice();

    double resetPaid();

    double paid();

    double pay(double money);

    void sell(boolean force);

    default void sell() {
        sell(false);
    }

    default boolean isAlreadyPaid() {
        return paid() >= getPrice();
    }

    default double moneyChange() {
        return paid() - getPrice();
    }

    Map<ItemData, Integer> itemsInCart();

    void reset();
}

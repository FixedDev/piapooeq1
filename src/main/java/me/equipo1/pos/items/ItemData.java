package me.equipo1.pos.items;

public interface ItemData {

    String id();

    String name();

    void setName(String name);

    double price();

    void setPrice(double price);

    int minQuantity();

    void setMinStock(int minStock);

    default boolean shouldRestock(int stock) {
        return minQuantity() != -1 && stock <= minQuantity();
    }
}

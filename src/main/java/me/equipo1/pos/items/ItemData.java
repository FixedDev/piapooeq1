package me.equipo1.pos.items;

public interface ItemData {
    int autoid();

    void setAutoId(int id);

    String id();

    String name();

    double price();

    void setPrice(double price);


    int minQuantity();

    default boolean shouldRestock(int stock) {
        return minQuantity() != -1 && stock <= minQuantity();
    }
}

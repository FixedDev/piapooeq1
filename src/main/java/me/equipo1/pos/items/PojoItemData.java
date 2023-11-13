package me.equipo1.pos.items;

import java.util.Objects;

public class PojoItemData implements ItemData {

    private final String id;
    private  String name;
    private double price;

    private  int minQuantity;

    public PojoItemData(String id, String name, double price, int minQuantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.minQuantity = minQuantity;
    }

    @Override
    public String id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public double price() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }


    @Override
    public int minQuantity() {
        return minQuantity;
    }

    @Override
    public void setMinStock(int minStock) {
        this.minQuantity = minStock;
    }
}

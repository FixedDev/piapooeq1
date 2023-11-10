package me.equipo1.pos.items;

public class PojoItemData implements ItemData {

    private int autoid;
    private final String id;
    private final String name;
    private double price;

    private final int minQuantity;

    public PojoItemData(int autoid, String id, String name, double price, int minQuantity) {
        this.autoid = autoid;
        this.id = id;
        this.name = name;
        this.price = price;
        this.minQuantity = minQuantity;
    }

    @Override
    public int autoid() {
        return autoid;
    }

    @Override
    public void setAutoId(int id) {
        this.autoid = id;
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
}

package me.equipo1.pos.sales;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.items.ItemData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PojoCart implements Cart {

    private final Inventory inventory;
    private final Map<ItemData, Integer> itemsWithQuantity;

    private double paid = 0;

    public PojoCart(Inventory inventory) {
        this.inventory = inventory;
        itemsWithQuantity = new HashMap<>();
    }

    @Override
    public void addItem(String id) {
        Map.Entry<ItemData, Integer> parsed = parse(id);

        ItemData data = parsed.getKey();
        int quantity = parsed.getValue();

        itemsWithQuantity.compute(data, (itemData, integer) -> (integer == null ? 0 : integer) + quantity);
    }

    @Override
    public void removeItem(String id) {
        Map.Entry<ItemData, Integer> parsed = parse(id);

        ItemData data = parsed.getKey();
        int quantity = parsed.getValue();

        itemsWithQuantity.computeIfPresent(data, (itemData, integer) -> quantity > integer ? 0 : integer - quantity);
    }

    @Override
    public void itemOperation(String input) {
        if (input.startsWith("D") || input.startsWith("-")) {
            removeItem(input.substring(1));
        } else {
            addItem(input);
        }
    }

    private Map.Entry<ItemData, Integer> parse(String id) throws IllegalArgumentException {
        int multiplyIdx = id.indexOf('*');
        int quantity = 1;

        if (multiplyIdx != -1) {
            String quantityStr = id.substring(0, multiplyIdx);
            id = id.substring(multiplyIdx + 1);

            quantity = Integer.parseInt(quantityStr);
        }

        Optional<ItemData> optItem = inventory.itemById(id);

        if (optItem.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return Map.entry(optItem.get(), quantity);
    }

    @Override
    public double getPrice() {
        double price = 0;

        for (Map.Entry<ItemData, Integer> entry : itemsWithQuantity.entrySet()) {
            ItemData data = entry.getKey();
            int quantity = entry.getValue();

            price += data.price() * quantity;
        }


        return price;
    }

    @Override
    public double resetPaid() {
        double last = paid;
        paid = 0;

        return last;
    }

    @Override
    public double paid() {
        return paid;
    }

    @Override
    public double pay(double money) {
        paid += money;

        return paid - money;
    }

    @Override
    public void sell(boolean force) {
        if (!isAlreadyPaid() && !force) {
            return;
        }

        itemsWithQuantity.forEach(inventory::sell);
    }

    @Override
    public Map<ItemData, Integer> itemsInCart() {
        return itemsWithQuantity;
    }

    @Override
    public void reset() {
        itemsWithQuantity.clear();
        resetPaid();
    }

    @Override
    public void returnItems() {
        itemsWithQuantity.forEach(
                (itemData, integer) -> {
                    inventory.returnItem(itemData, integer);
                }
        );
    }
}

package me.equipo1.pos.inventory;

import me.equipo1.pos.items.ItemData;
import me.equipo1.pos.items.ItemRegistry;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PojoInventory implements Inventory {

    private final ItemRegistry registry;
    private final Map<String, Integer> itemsStock;
    private final Set<String> changedStocks;

    public PojoInventory(ItemRegistry registry, Map<String, Integer> itemsStock) {
        this.registry = registry;
        this.itemsStock = itemsStock;

        changedStocks = new HashSet<>();
    }

    @Override
    public Optional<ItemData> itemById(String id) {
        return registry.getIfCached(id);
    }

    @Override
    public void registerItem(ItemData itemData) {
        if (registry.getIfCached(itemData.id()).isPresent()) {
            return;
        }

        registry.saveItem(itemData);
    }

    @Override
    public void deleteItem(ItemData itemData) {
        if (registry.getIfCached(itemData.id()).isEmpty()) {
            return;
        }

        registry.deleteItem(itemData);
    }

    @Override
    public void addItem(ItemData itemData, int quantity) {
        if (itemById(itemData.id()).isEmpty()) {
            return;
        }

        itemsStock.compute(itemData.id(), (s, integer) -> (integer == null ? 0 : integer) + quantity);
        changedStocks.add(itemData.id());
    }

    @Override
    public boolean sell(ItemData itemData, int quantity) {
        if (itemById(itemData.id()).isEmpty()) {
            return false;
        }

        int itemStock = itemsStock.getOrDefault(itemData.id(), 0);
        if (itemStock - quantity < 0) {
            return false;
        }

        itemsStock.compute(itemData.id(), (s, integer) -> (integer == null ? 0 : integer) - quantity);
        changedStocks.add(itemData.id());

        return true;
    }

    @Override
    public boolean sell(ItemData itemData, int quantity, boolean force) {
        if (itemById(itemData.id()).isEmpty()) {
            return false;
        }

        int itemStock = itemsStock.getOrDefault(itemData.id(), 0);
        if (itemStock - quantity < 0 && !force) {
            return false;
        }

        itemsStock.compute(itemData.id(), (s, integer) -> (integer == null ? 0 : integer) - quantity);
        changedStocks.add(itemData.id());

        return true;    }

    @Override
    public boolean has(ItemData itemData, int quantity) {
        if (itemById(itemData.id()).isEmpty()) {
            return false;
        }

        int itemStock = itemsStock.getOrDefault(itemData.id(), 0);
        return itemStock > quantity;
    }

    @Override
    public int stock(ItemData itemData) {
        return itemsStock.getOrDefault(itemData.id(), 0);
    }

    @Override
    public boolean returnItem(ItemData itemData, int quantity) {
        if (itemById(itemData.id()).isEmpty()) {
            return false;
        }

        itemsStock.compute(itemData.id(), (s, integer) -> (integer == null ? 0 : integer) + quantity);
        changedStocks.add(itemData.id());

        return true;
    }

    public Map<String, Integer> stock() {
        return itemsStock;
    }

    public Set<String> changedStocks() {
        return changedStocks;
    }
}

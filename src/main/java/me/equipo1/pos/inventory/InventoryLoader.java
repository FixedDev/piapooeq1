package me.equipo1.pos.inventory;

import me.equipo1.pos.datastore.Datastore;
import me.equipo1.pos.items.ItemRegistry;

import java.util.concurrent.CompletableFuture;

public class InventoryLoader {
    private final ItemRegistry registry;
    private final Datastore datastore;

    public InventoryLoader(ItemRegistry registry, Datastore datastore) {
        this.registry = registry;
        this.datastore = datastore;
    }

    public CompletableFuture<Inventory> loadInventory() {
        return registry.load().thenCombine(datastore.loadInventory(), (unused, itemData) -> new PojoInventory(registry, itemData));
    }
}

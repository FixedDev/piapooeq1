package me.equipo1.pos.datastore;

import me.equipo1.pos.inventory.Inventory;
import me.equipo1.pos.items.ItemData;

import java.util.concurrent.CompletableFuture;

public interface Datastore {
    CompletableFuture<Inventory> loadInventory();
    CompletableFuture<Void> saveInventory(Inventory inventory);

    CompletableFuture<Void> deleteItem(ItemData itemData);

    CompletableFuture<Void> saveItem(ItemData itemData);

    CompletableFuture<ItemData> findItem(int autoId);

    CompletableFuture<ItemData> findItem(String id);
}
